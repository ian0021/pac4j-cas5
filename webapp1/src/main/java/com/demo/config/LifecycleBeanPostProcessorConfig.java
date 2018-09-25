package com.demo.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
/**
 * Shiro生命周期处理器,开启了就会启动报错不知道为什么？---设置为优先启动！！见：LifecycleBeanPostProcessorConfig
 * @Description:TODO
 */
@Configuration
@Order(1)
public class LifecycleBeanPostProcessorConfig {
  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
      return new LifecycleBeanPostProcessor();
  }
}
