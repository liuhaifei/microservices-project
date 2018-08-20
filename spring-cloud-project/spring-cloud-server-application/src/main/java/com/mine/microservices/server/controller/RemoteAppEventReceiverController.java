package com.mine.microservices.server.controller;


import com.mine.microservices.server.bus.event.SenderRemoteAppEvent;
import com.mine.microservices.server.bus.listener.HttpRemoteAppReceiverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName RemoteAppEventSenderController
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 14:57
 * @Version 1.0
 **/
@RestController
public class RemoteAppEventReceiverController implements ApplicationEventPublisherAware {

    private Logger logger= LoggerFactory.getLogger(RemoteAppEventReceiverController.class);
    private ApplicationEventPublisher publisher;


    @PostMapping("/receive/remote/event/")
    public String receive(@RequestBody Map<String,Object> data){//REST 请求不需要具体类型
        //事件发送者
        String sender=(String) data.get("sender");
        //事件的数据内容
        Object value=data.get("value");
        //事件类型
        String type=(String) data.get("type");

        logger.info("接收到Event事件发送过来的http请求");
        publisher.publishEvent(new SenderRemoteAppEvent(value,sender));

        return "received";
    }
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher=applicationEventPublisher;
    }
}
