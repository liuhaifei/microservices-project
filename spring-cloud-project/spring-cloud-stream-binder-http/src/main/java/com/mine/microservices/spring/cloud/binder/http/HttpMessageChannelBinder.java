package com.mine.microservices.spring.cloud.binder.http;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.client.RestTemplate;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;


/**
 * @ClassName RocketMQMessageChannelBinder
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 18:24
 * @Version 1.0
 **/
public class HttpMessageChannelBinder implements Binder<MessageChannel, ConsumerProperties, ProducerProperties> {

    private static final String TARGET_APP_NAME="spring-cloud-server-application";


    private DiscoveryClient discoveryClient;
    private MessageReceiverController controller;


    private static final String NAME_ADDRESS="localhost:9876";


    public HttpMessageChannelBinder(DiscoveryClient discoveryClient, MessageReceiverController controller) {
        this.discoveryClient = discoveryClient;
        this.controller = controller;
    }
    /**
     * 随机负载均衡算
     * @param serviceName
     * @return
     */
    private ServiceInstance chooseServiceInstanceRandomly(String serviceName) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
        int size = serviceInstances.size();
        int index = new Random().nextInt(size);
        return serviceInstances.get(index);
    }

    private String getTargetRootURL(String serviceName) {
        ServiceInstance serviceInstance = chooseServiceInstanceRandomly(serviceName);
        return serviceInstance.isSecure() ?
                "https://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() :
                "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
    }
    @Override
    public Binding<MessageChannel> bindConsumer(String name, String group, MessageChannel inputChannel, ConsumerProperties consumerProperties) {

        //给controller注入 MessageChannel
        controller.setMessageChannel(inputChannel);


        return null;
    }

    @Override
    public Binding<MessageChannel> bindProducer(String name, MessageChannel outputChannel, ProducerProperties producerProperties) {

        RestTemplate restTemplate=new RestTemplate();

        SubscribableChannel subscribableChannel=(SubscribableChannel)outputChannel;

        subscribableChannel.subscribe(message -> {
            //消息体
            byte[] messageBody=(byte[])message.getPayload();
            //对象的服务名称 ->IP:PORT
            String rootURL=getTargetRootURL(TARGET_APP_NAME);
            //ENDPOINT URI:/message/receive
            String targetURI=rootURL+ MessageReceiverController.ENDPOINT_URI;
            //请求实体 post
            try {
                RequestEntity requestEntity=new RequestEntity(messageBody, HttpMethod.POST,new URI(targetURI));
                restTemplate.exchange(requestEntity,String.class);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        return null;
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
