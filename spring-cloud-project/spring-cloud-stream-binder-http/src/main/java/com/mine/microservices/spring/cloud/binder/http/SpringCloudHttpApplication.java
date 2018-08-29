package com.mine.microservices.spring.cloud.binder.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication //spring boot 应用
//@EnableScheduling      //激活定时
public class SpringCloudHttpApplication
{
    public static void main( String[] args )
    {

        SpringApplication.run(SpringCloudHttpApplication.class,args);
    }


}
