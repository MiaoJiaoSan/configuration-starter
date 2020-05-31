package com.miaojiaosan.config.spring.boot.autoconfigure;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis Config Properties
 * @author miaojiaosan
 */
@ConfigurationProperties(prefix = ConfigRedisProperties.REDIS_CONFIG)
public class ConfigRedisProperties {

  public static final String REDIS_CONFIG = "config.redis";

  public String group;

  public String dataId;

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getDataId() {
    return dataId;
  }

  public void setDataId(String dataId) {
    this.dataId = dataId;
  }
}
