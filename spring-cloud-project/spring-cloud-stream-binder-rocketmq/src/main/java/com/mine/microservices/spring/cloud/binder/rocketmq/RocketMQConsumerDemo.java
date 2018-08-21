//package com.mine.microservices.spring.cloud.binder.rocketmq;
//
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//
//import java.util.List;
//
///**
// * @ClassName RocketMQProducerDemo
// * @Description TODO
// * @Author 刘海飞
// * @Date 2018/8/20 18:13
// * @Version 1.0
// **/
//public class RocketMQConsumerDemo {
//
//    public static void main(String[] args) throws Exception {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("testGrop");
//
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//
//        //set to broadcast mode
//        consumer.setMessageModel(MessageModel.BROADCASTING);
//
//        consumer.subscribe("TopicTest", "TagA");
//
//        consumer.setNamesrvAddr("127.0.0.1:9876");
//
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
//                                                            ConsumeConcurrentlyContext context) {
//                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//
//        consumer.start();
//        System.out.printf("Broadcast Consumer Started.%n");
//    }
//}
