package com.miaojiaosan.config.spring.boot;

import com.miaojiaosan.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisConfigTest {

  @Resource
  ApplicationContext applicationContext;

  @Test
  public void test(){
    assert applicationContext != null;
    RedisTemplate template = (RedisTemplate)applicationContext.getBean("redisTemplate");
    assert template != null;
//    RefreshScopeRegistry bean = applicationContext.getBean(RefreshScopeRegistry.class);
//    assert bean != null;
  }
}
