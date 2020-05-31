package com.miaojiaosan.config.spring.boot.client;

import com.miaojiaosan.config.spring.boot.autoconfigure.ConfigRedisProperties;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis Config Client
 * @author miaojiaosan
 */
public class RedisConfigClient {

  /**
   * {@link RedisTemplate}
   */
  public final RedisTemplate template;

  public RedisConfigClient(ConfigRedisProperties properties, RedisTemplate template){
    this.template = template;
  }

}
