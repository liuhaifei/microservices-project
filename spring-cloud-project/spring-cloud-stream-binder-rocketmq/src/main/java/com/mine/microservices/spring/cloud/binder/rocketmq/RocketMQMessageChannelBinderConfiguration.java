package com.mine.microservices.spring.cloud.binder.rocketmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RocketMQMessageChannelBinderConfiguration
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 18:22
 * @Version 1.0
 **/
@Configuration
public class RocketMQMessageChannelBinderConfiguration {

    @Bean
    public RocketMQMessageChannelBinder rocketMQMessageChannelBinder(){
        return new RocketMQMessageChannelBinder();
    }
}
