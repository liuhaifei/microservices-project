package com.mine.microservices.client.controller;

import com.mine.microservices.client.bus.event.RemoteAppEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName RemoteAppEventSenderController
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 14:57
 * @Version 1.0
 **/
public class RemoteAppEventSenderController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @GetMapping("/send/remote/event")
    public String sendEvent(@RequestParam String message){
        publisher.publishEvent(message);
        return "Sent";
    }
    @PostMapping("/send/remote/event/{appName}")
    public String sendAppCluster(@PathVariable String appName, @RequestBody Object data){
        RemoteAppEvent remoteAppEvent=new RemoteAppEvent(data,appName,true);
        publisher.publishEvent(remoteAppEvent);
        return "OK";
    }
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher=applicationEventPublisher;
    }
}
