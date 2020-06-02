package com.miaojiaosan.config.redis;

import com.miaojiaosan.config.redis.app.Application;
import com.miaojiaosan.config.redis.app.SpringBean;
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
  public void test() throws InterruptedException {
    long time = System.currentTimeMillis();
    while (true) {
      context.getBean(SpringBean.class);
      if(System.currentTimeMillis() - time > 120000)
      Thread.sleep(10000);
    }
  }

}
