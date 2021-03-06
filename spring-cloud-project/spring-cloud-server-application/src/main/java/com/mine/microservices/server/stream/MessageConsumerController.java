package com.mine.microservices.server.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.StreamListener;
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

    //自定义渠道
//    @Autowired
//    private SimpleMessageConsumer simpleMessageConsumer;

    @Autowired
    @Qualifier(Sink.INPUT) // Bean 名称
    private SubscribableChannel subscribableChannel;

    @PostConstruct
    public void init(){
        SubscribableChannel subscribableChannel= sink.input();
        subscribableChannel.subscribe(message -> {
            String string=(String) message.getPayload();
            System.out.println("接收到消息："+string);
        });
    }

    @StreamListener("myChannel1")  // Spring Cloud Stream 注解驱动
    public void onMessage(String message) {
        System.out.println("onMessage(String): " + message);
    }

    @StreamListener("01test")  // Spring Cloud Stream 注解驱动
    public void test(String message) {
        System.out.println("01test(String): " + message);
    }


    @StreamListener("testrocket")  // Spring Cloud Stream 注解驱动
    public void testrocket(String message)
    {
        System.out.println("testrocket(String): " + message);
    }

    @StreamListener("http")  // Spring Cloud Stream 注解驱动
    public void testHttp(String message)
    {
        System.out.println("testHttp(String): " + message);
    }
}
