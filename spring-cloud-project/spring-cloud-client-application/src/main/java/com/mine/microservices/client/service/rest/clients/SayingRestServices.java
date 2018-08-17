package com.mine.microservices.client.service.rest.clients;

import com.mine.microservices.client.annotation.RestClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName SayingServices
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/17 10:43
 * @Version 1.0
 **/
@RestClient(name="spring-cloud-server-application")
public interface SayingRestServices {

    @GetMapping("/say")
    String say(@RequestParam("message") String message);
}
