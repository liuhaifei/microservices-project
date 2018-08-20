package com.mine.microservices.client.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SpringEventController
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 14:48
 * @Version 1.0
 **/
@RestController
public class SpringEventController implements ApplicationEventPublisherAware{

    private ApplicationEventPublisher publisher;

    public String sendEvent(@RequestParam String message){
        publisher.publishEvent(message);
        return "sent";
    }
    @EventListener
    public void onMessage(PayloadApplicationEvent event){
        System.out.println("监听到消息："+event.getPayload());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher=applicationEventPublisher;
    }
}
