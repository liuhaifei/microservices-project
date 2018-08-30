package com.mine.microservices.server.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @ClassName SimpleMessageConsumer
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/16 17:41
 * @Version 1.0
 **/
public interface SimpleMessageConsumer {

    @Input("myChannel1")
    SubscribableChannel input();

    @Input("testrocket")
    SubscribableChannel testrocket();

    @Input("http")
    SubscribableChannel http();
}
