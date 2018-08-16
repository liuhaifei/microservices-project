package com.mine.microservices.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName SpringCloudServerApplication
 * @Description 启动类
 * @Author 刘海飞
 * @Date 2018/8/13 11:44
 * @Version 1.0
 **/
@SpringBootApplication //spring boot 应用
@EnableDiscoveryClient //激活服务发现客户端
@EnableHystrix //激活 Hystrix
@EnableAspectJAutoProxy(proxyTargetClass = true) //激活AOP
@EnableBinding(Sink.class)
public class SpringCloudServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudServerApplication.class)
                    .web(WebApplicationType.SERVLET)
                    .run(args);

    }
}
