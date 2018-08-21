package com.mine.microservices.spring.cloud.binder.rocketmq;

import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.messaging.MessageChannel;

/**
 * @ClassName RocketMQMessageChannelBinder
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 18:24
 * @Version 1.0
 **/
public class RocketMQMessageChannelBinder implements Binder<MessageChannel, ConsumerProperties, ProducerProperties> {

    @Override
    public Binding<MessageChannel> bindConsumer(String name, String group, MessageChannel inboundBindTarget, ConsumerProperties consumerProperties) {
        return null;
    }

    @Override
    public Binding<MessageChannel> bindProducer(String name, MessageChannel outboundBindTarget, ProducerProperties producerProperties) {
        return null;
    }
}
