package com.mine.microservices.spring.cloud.binder.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @ClassName RocketMQMessageChannelBinder
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 18:24
 * @Version 1.0
 **/
public class RocketMQMessageChannelBinder implements Binder<MessageChannel, ConsumerProperties, ProducerProperties> {

    private static final String GROUP="test_group";

    private static final String TOPIC="TEST_TOPIC";

    private static final String TAG="TEST_TAG";

    private static final String NAME_ADDRESS="localhost:9876";
    @Override
    public Binding<MessageChannel> bindConsumer(String name, String group, MessageChannel inputChannel, ConsumerProperties consumerProperties) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP);

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //set to broadcast mode
        consumer.setMessageModel(MessageModel.BROADCASTING);
        try {
            consumer.subscribe(TOPIC, TAG);

            consumer.setNamesrvAddr(NAME_ADDRESS);

            consumer.registerMessageListener(new MessageListenerConcurrently() {

                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {
                    System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");

                    msgs.forEach(msg->{
                        byte[] body=msg.getBody();
                        inputChannel.send(new GenericMessage<Object>(body));
                    });
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            consumer.start();

        }catch (Exception e){
            e.printStackTrace();
        }



        return ()->{
            System.out.println("consumer shutdown....");
            consumer.shutdown();
        };
    }

    @Override
    public Binding<MessageChannel> bindProducer(String name, MessageChannel outputChannel, ProducerProperties producerProperties) {

        DefaultMQProducer producer = new DefaultMQProducer(GROUP);

        producer.setNamesrvAddr(NAME_ADDRESS);

        try{
            producer.start();

            SubscribableChannel subscribableChannel=(SubscribableChannel)outputChannel;
            subscribableChannel.subscribe(message -> {
                Object messageBody=message.getPayload();
                Message mqMessage=new Message();
                mqMessage.setTopic(TOPIC);
                mqMessage.setTags(TAG);
                try {
                    mqMessage.setBody(serialize(messageBody));
                    SendResult sendResult = producer.send(mqMessage);
                    System.out.printf("消息发送：%s%n", sendResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return ()->{
            System.out.println("producer shutdown");
            producer.shutdown();
        };
    }

    private byte[] serialize(Object serializable) throws IOException {

        if (serializable instanceof byte[]) {
            return (byte[]) serializable;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        // 通过 Java 序列化 将 Object 写入字节流
        objectOutputStream.writeObject(serializable);
        // 返回字节数组
        return outputStream.toByteArray();
    }
}
