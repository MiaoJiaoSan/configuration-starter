package com.miaojiaosan.config.spring.boot.scope;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.lang.NonNull;

/**
 * 注册{@link RefreshScope}
 * @author miaojiaosan
 */
public class RefreshScopeRegistry implements BeanDefinitionRegistryPostProcessor {


  private BeanDefinitionRegistry registry;

  @Override
  public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
    this.registry = registry;
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    //注册RefreshScope 为SpringScope
    beanFactory.registerScope(RefreshScope.NAME,new RefreshScope());
  }

  public BeanDefinitionRegistry getBeanDefinitionRegistry(){
    return this.registry;
  }



}
