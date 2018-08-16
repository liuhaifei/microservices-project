package com.mine.microservices.server.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @ClassName MessageConsumerController
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/16 16:33
 * @Version 1.0
 **/
@RestController
public class MessageConsumerController {

    @Autowired
    private Sink sink;

    @PostConstruct
    public void init(){
        SubscribableChannel subscribableChannel= sink.input();
        subscribableChannel.subscribe(message -> {
            byte[] bytes=(byte[])message.getPayload();
            System.out.println("接收到消息："+new String(bytes));
        });
    }
}
