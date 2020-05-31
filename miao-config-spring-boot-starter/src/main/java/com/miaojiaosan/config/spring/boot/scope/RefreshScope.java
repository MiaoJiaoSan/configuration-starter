package com.miaojiaosan.config.spring.boot.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Refresh Scope 需要动态刷新Bean的Scope
 * @author miaojiaosan
 */
public class RefreshScope implements Scope {


  private ConcurrentHashMap<String,Object> container = new ConcurrentHashMap<>();

  @Override
  public Object get(String name, ObjectFactory<?> objectFactory) {
    //如果容器中包含BeanName 直接获取
    if(container.containsKey(name)) {
      return container.get(name);
    }
    //否则创建后加入容器
    Object bean = objectFactory.getObject();
    container.put(name,bean);
    return bean;
  }

  @Override
  public Object remove(String name) {
    //根据BeanName 从容器中移除Bean
    return container.remove(name);
  }

  @Override
  public void registerDestructionCallback(String name, Runnable callback) {

  }

  @Override
  public Object resolveContextualObject(String key) {
    return null;
  }

  @Override
  public String getConversationId() {
    return null;
  }
}
