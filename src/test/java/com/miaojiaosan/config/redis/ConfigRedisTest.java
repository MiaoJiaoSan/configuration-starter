package com.miaojiaosan.config.redis;

import com.miaojiaosan.config.spring.boot.app.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ConfigRedisTest {

  @Autowired
  private ApplicationContext context;

  @Test
  public void test(){
    assert null != context.getBean("redisTemplate");
  }

}
