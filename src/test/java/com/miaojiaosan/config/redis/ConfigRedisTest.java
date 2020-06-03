package com.miaojiaosan.config.redis;

import com.miaojiaosan.config.redis.app.Application;
import com.miaojiaosan.config.redis.app.SpringBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ConfigRedisTest {

  @Autowired
  private ApplicationContext context;



  public static void main(String[] args) {
    Jedis jedis = new Jedis("localhost",9736);
    jedis.auth("redis");
    jedis.del("test");
    jedis.hset("test",new HashMap<String, String>(){{
      put("spring.application.name","source_web");
    }});
    jedis.close();
  }

  @Test
  public void test() throws InterruptedException {
    long time = System.currentTimeMillis();
    while (true) {
      context.getBean(SpringBean.class);
      if(System.currentTimeMillis() - time > 60000){
        break;
      }
      Thread.sleep(10000);
    }
  }
}
