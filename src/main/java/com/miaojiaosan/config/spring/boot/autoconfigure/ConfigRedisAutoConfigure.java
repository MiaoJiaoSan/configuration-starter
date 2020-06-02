package com.miaojiaosan.config.spring.boot.autoconfigure;

import com.miaojiaosan.config.spring.boot.client.RedisConfigClient;
import com.miaojiaosan.config.spring.boot.scope.RefreshScopeRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.util.Objects;

/**
 *
 * @author miaojiaosan
 */
@Configuration
@EnableConfigurationProperties(ConfigRedisProperties.class)
@ConditionalOnProperty(prefix = ConfigRedisProperties.REDIS_CONFIG, value = "enable", matchIfMissing = true)
public class ConfigRedisAutoConfigure {



  @Bean
  public RedisConfigClient redisConfigClient(
      ConfigRedisProperties properties,
      RefreshScopeRegistry refreshScopeRegistry
  ){
    Jedis jedis = new Jedis(properties.getHost(), properties.getPort());
    String password = Objects.nonNull(password = properties.getPassword())?
        password:"";
    jedis.auth(password);
    return new RedisConfigClient(properties, refreshScopeRegistry, jedis);
  }

  @Bean
  public RefreshScopeRegistry refreshScopeRegistry(){
    return new RefreshScopeRegistry();
  }



}






