package com.mine.microservices.client.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ClassName EnableRestClient
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/17 11:08
 * @Version 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RestClientsRegistrar.class)
public @interface EnableRestClient {

    Class<?>[] clients() default {};
}
