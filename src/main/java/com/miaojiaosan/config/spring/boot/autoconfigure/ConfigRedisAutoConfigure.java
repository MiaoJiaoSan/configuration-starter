package com.miaojiaosan.config.spring.boot.autoconfigure;

import com.miaojiaosan.config.spring.boot.client.RedisConfigClient;
import com.miaojiaosan.config.spring.boot.scope.RefreshScopeRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 * @author miaojiaosan
 */
@Configuration
@EnableConfigurationProperties(ConfigRedisProperties.class)
@ConditionalOnProperty(prefix = ConfigRedisProperties.REDIS_CONFIG, value = "enable", matchIfMissing = true)
public class ConfigRedisAutoConfigure {

  @Bean
  public RefreshScopeRegistry refreshScopeRegistry(){
    return new RefreshScopeRegistry();
  }

  @Bean
  @SuppressWarnings({ "rawtypes"})
  public RedisConfigClient redisConfigClient(
      ConfigRedisProperties properties,
      RefreshScopeRegistry refreshScopeRegistry,
      @Qualifier("redisTemplate") RedisTemplate template
  ){
    return new RedisConfigClient(properties, refreshScopeRegistry, template);
  }

}






