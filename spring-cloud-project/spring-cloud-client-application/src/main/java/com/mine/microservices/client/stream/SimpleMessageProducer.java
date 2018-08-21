package com.mine.microservices.client.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @ClassName SimpleMessageProducer
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/16 17:39
 * @Version 1.0
 **/
public interface SimpleMessageProducer {

    @Output("myChannel")
    MessageChannel output();

    @Output("testrocket")
    MessageChannel testrocket();
}


