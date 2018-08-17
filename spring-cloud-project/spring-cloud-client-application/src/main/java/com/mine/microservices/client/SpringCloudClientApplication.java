package com.mine.microservices.client;

import com.mine.microservices.client.stream.MessageProducerController;
import com.mine.microservices.client.stream.SimpleMessageProducer;
import com.mine.microservices.client.stream.SimpleMessageTestProducer;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication //spring boot 应用
@EnableDiscoveryClient //激活服务发现客户端
@EnableScheduling      //激活定时
@EnableFeignClients     //激活Feign
@EnableBinding({Source.class, SimpleMessageProducer.class, SimpleMessageTestProducer.class}) //激活Binding
public class SpringCloudClientApplication
{
    public static void main( String[] args )
    {

        new SpringApplicationBuilder(SpringCloudClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
