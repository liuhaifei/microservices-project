package com.mine.microservices.spring.cloud.binder.http;

import org.springframework.cloud.client.discovery.DiscoveryClient;
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
public class HttpMessageChannelBinderConfiguration {

    @Bean
    public HttpMessageChannelBinder rocketMQMessageChannelBinder(
            DiscoveryClient discoveryClient,
            MessageReceiverController controller){
        return new HttpMessageChannelBinder(discoveryClient,controller);
    }
}
