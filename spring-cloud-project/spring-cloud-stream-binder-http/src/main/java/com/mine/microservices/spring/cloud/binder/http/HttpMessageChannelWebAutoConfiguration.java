package com.mine.microservices.spring.cloud.binder.http;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName HttpMessageChannelWebAutoConfiguration
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/30 15:39
 * @Version 1.0
 **/
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class HttpMessageChannelWebAutoConfiguration {

    @Bean
    public MessageReceiverController controller(){
        return new MessageReceiverController();
    }
}
