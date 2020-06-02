package com.miaojiaosan.config.spring.boot.autoconfigure;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis Config Properties
 * @author miaojiaosan
 */
@ConfigurationProperties(prefix = ConfigRedisProperties.REDIS_CONFIG)
public class ConfigRedisProperties {

  public static final String REDIS_CONFIG = "config.redis";

  public String host;

  public Integer port;

  public String password;

  public Boolean enable = false;

  public String group;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

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
