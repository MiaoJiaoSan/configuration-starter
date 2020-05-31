package com.miaojiaosan.config.spring.boot.autoconfigure;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis Config Properties
 * @author miaojiaosan
 */
@ConfigurationProperties(prefix = ConfigRedisProperties.REDIS_CONFIG)
public class ConfigRedisProperties {

  public static final String REDIS_CONFIG = "config.redis";

  public Boolean enable = false;

  public String group;

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }
}
