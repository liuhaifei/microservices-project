package com.mine.microservices.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @ClassName ServerController
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/15 14:24
 * @Version 1.0
 **/
@RestController
public class ServerController {

    private final static Random random=new Random();

    @GetMapping("/say")
    public String say(@RequestParam String message){
        System.out.println("serverController接收到消息-say:"+message);
        return "hello, "+message;
    }
}
