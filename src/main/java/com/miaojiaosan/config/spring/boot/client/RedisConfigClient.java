package com.miaojiaosan.config.spring.boot.client;

import com.miaojiaosan.config.spring.boot.autoconfigure.ConfigRedisProperties;
import com.miaojiaosan.config.spring.boot.scope.RefreshScopeRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Redis Config Client
 * @author miaojiaosan
 */
public class RedisConfigClient implements ApplicationContextAware {

  public static final String MIAO_SOURCE = "miaoSource";
  /**
   * {@link RedisTemplate}
   */
  private final RedisTemplate template;

  private final ConfigRedisProperties properties;

  private BeanDefinitionRegistry registry;

  private ConfigurableApplicationContext context;

  private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
  /**
   * miao properties map
   */
  private ConcurrentHashMap miaoProperties = new ConcurrentHashMap();

  public RedisConfigClient(
      ConfigRedisProperties properties,
      RefreshScopeRegistry refreshScopeRegistry,
      RedisTemplate template){
    this.registry = refreshScopeRegistry.getBeanDefinitionRegistry();
    this.properties = properties;
    this.template = template;
  }

  @PostConstruct
  public void init(){
    //enable = false
    if(!properties.getEnable()){return;}
    // group not exist
    String group = properties.getGroup();
    if(Objects.isNull(group)||!template.hasKey(group)){
      return;
    }
    //拉取属性前置工作
    preparePullProperties(group);

    pullProperties(group);
    //启用定时任务
    executor.scheduleAtFixedRate(()->{
      Map properties = template.opsForHash().entries(group);
      miaoProperties.putAll(properties);
    },5,5, TimeUnit.SECONDS);

  }
  private void preparePullProperties(String group) {
    //检查environment中是否存在miaoMap
    if(checkExistsSpringProperty()){
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

  @SuppressWarnings({ "rawtypes", "unchecked"})
  private void pullProperties(String group) {
    MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
    PropertySource<?> propertySource = propertySources.get(MIAO_SOURCE);
    //这里英国不可能为空,因为前面创建了miaoProperties放进了environment中
    ConcurrentHashMap miaoProperties = (ConcurrentHashMap) propertySource.getSource();
    Map properties = template.opsForHash().entries(group);
    miaoProperties.putAll(properties);
  }

  @Override
  public void setApplicationContext(ApplicationContext context) throws BeansException {
    this.context = (ConfigurableApplicationContext) context;
  }
}
