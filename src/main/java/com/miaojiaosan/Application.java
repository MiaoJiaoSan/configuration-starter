package com.miaojiaosan;

import com.miaojiaosan.config.spring.boot.autoconfigure.ConfigRedisProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigRedisProperties.class)
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
