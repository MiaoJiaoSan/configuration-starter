package com.miaojiaosan.config.spring.boot.client;

import com.miaojiaosan.config.spring.boot.autoconfigure.ConfigRedisProperties;
import com.miaojiaosan.config.spring.boot.scope.RefreshScope;
import com.miaojiaosan.config.spring.boot.scope.RefreshScopeRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Redis Config Client
 * @author miaojiaosan
 */
public class RedisConfigClient implements ApplicationContextAware {

  public static final String MIAO_SOURCE = "miaoSource";
  /**
   * {@link Jedis}
   */
  private final Jedis jedis;

  private final ConfigRedisProperties properties;

  private BeanDefinitionRegistry registry;

  private ConfigurableApplicationContext context;

  private static final ScheduledExecutorService EXECUTOR = new ScheduledThreadPoolExecutor(1);
  /**
   * miao properties map
   */
  private ConcurrentHashMap<String,String> miaoProperties = new ConcurrentHashMap<>();

  public RedisConfigClient(
      ConfigRedisProperties properties,
      RefreshScopeRegistry refreshScopeRegistry,
      Jedis jedis){
    this.registry = refreshScopeRegistry.getBeanDefinitionRegistry();
    this.properties = properties;
    this.jedis = jedis;
  }

  @PostConstruct
  public void init(){
    //enable = false
    if(!properties.getEnable()){return;}
    // group not exist
    String group = properties.getGroup();
    Boolean exists = jedis.exists(group);
    jedis.close();
    //如果分组不存在直接返回
    if(Objects.isNull(exists)||!exists){
      return;
    }
    //拉取属性前置工作
    preparePullProperties();
    //拉取properties
    pullProperties(group);
    //启用定时任务
    schedule(group);

  }

  private void schedule(String group) {
    EXECUTOR.scheduleAtFixedRate(()-> pullProperties(group),5,1, TimeUnit.SECONDS);
  }

  private void preparePullProperties() {
    //检查environment中是否存在miaoMap
    if(!checkExistsSpringProperty()){
      createMiaoProperty();
    }
  }

  private boolean checkExistsSpringProperty() {
    MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
    for (PropertySource<?> propertySource : propertySources) {
      if (MIAO_SOURCE.equals(propertySource.getName())) {
        return true;
      }
    }
    return false;
  }

  private void createMiaoProperty() {
    MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
    OriginTrackedMapPropertySource zookeeperSource = new OriginTrackedMapPropertySource(MIAO_SOURCE, miaoProperties);
    propertySources.addLast(zookeeperSource);
  }


  private void pullProperties(String group) {
    MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
    PropertySource<?> propertySource = propertySources.get(MIAO_SOURCE);
    if(Objects.isNull(propertySource)){ return;}
    @SuppressWarnings({ "rawtypes", "unchecked"})
    ConcurrentHashMap<String, Object> miaoProperties = (ConcurrentHashMap) propertySource.getSource();
    Map<String, String> properties = jedis.hgetAll(group);
    jedis.close();
    miaoProperties.putAll(properties);
    refreshBean();
  }

  @Override
  public void setApplicationContext(ApplicationContext context) throws BeansException {
    this.context = (ConfigurableApplicationContext) context;
  }

  private void refreshBean() {
    String[] beanDefinitionNames = context.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
      if(RefreshScope.NAME.equals(beanDefinition.getScope())) {
        //先删除,,,,思考，如果这时候删除了bean，有没有问题？
        context.getBeanFactory().destroyScopedBean(beanDefinitionName);
        //再实例化
        context.getBean(beanDefinitionName);
      }
    }
  }
}
