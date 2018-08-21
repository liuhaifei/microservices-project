package com.mine.microservices.client.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName MessageProducerBean
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/16 14:56
 * @Version 1.0
 **/
@RestController
public class MessageProducerController {

    @Autowired
    private Source source;

    //自定义渠道
    @Autowired
    private SimpleMessageProducer simpleMessageProducer;
    @Autowired
    private SimpleMessageTestProducer simpleMessageTestProducer;

//    @Autowired
//    @Qualifier(Source.OUTPUT) // Bean 名称
//    private MessageChannel messageChannel;


    @GetMapping("/send")
    public boolean send(@RequestParam String message){
       MessageChannel messageChannel= source.output();
        return messageChannel.send(new GenericMessage("send:"+message));
//        return messageChannel.send(MessageBuilder.withPayload(message).build());
    }

    @GetMapping("/send1")
    public boolean send1(@RequestParam String message){
        MessageChannel messageChannel= simpleMessageProducer.output();
        return messageChannel.send(new GenericMessage("send1:"+message));
//        return messageChannel.send(MessageBuilder.withPayload(message).build());
    }

    @GetMapping("/send2")
    public boolean send2(@RequestParam String message){
        MessageChannel messageChannel= simpleMessageTestProducer.output();
        return messageChannel.send(new GenericMessage("send2:"+message));
//        return messageChannel.send(MessageBuilder.withPayload(message).build());
    }
    @GetMapping("/send/testrocket")
    public boolean testrocket(@RequestParam String message){
        MessageChannel messageChannel= simpleMessageProducer.testrocket();
        return messageChannel.send(new GenericMessage("testrocket:"+message));
    }

}
