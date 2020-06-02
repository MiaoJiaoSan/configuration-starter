package com.miaojiaosan.config.redis.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Scope("refresh")
public class SpringBean {

  @Value("${spring.application.name}")
  private String name;

  @PostConstruct
  public void out(){
    System.out.println("======================="+name);
    System.out.println();
  }

//  @PreDestroy
//  public void destroy(){
//    System.out.println("=============destroy");
//  }

  public String getName() {
    return name;
  }
}
