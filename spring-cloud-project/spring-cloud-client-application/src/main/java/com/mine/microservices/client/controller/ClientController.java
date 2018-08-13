package com.mine.microservices.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName ClientController
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/13 16:06
 * @Version 1.0
 **/
public class ClientController {
    @Autowired
    private DiscoveryClient discoveryClient;
    //自定义restTemplate
    @Autowired
    private RestTemplate restTemplate;

    private volatile Set<String> targetUrls=new HashSet<>();
    private volatile Map<String,Set<String>> targetUrlCaches=new HashMap<>();

    @Value("${spring.application.name}")
    private String currentServiceName;

    @Scheduled(fixedRate=10*1000) //每10秒更新一次
    public void updateTargetUrlsCache(){
        //获取当前应用的机器列表
        Set<String> oldTargetUrls=this.targetUrls;
        List<ServiceInstance> serviceInstances= discoveryClient.getInstances(currentServiceName);
        Set<String> newTargetUrls=serviceInstances.stream()
                                    .map(s -> s.isSecure()?
                                        "https://"+s.getHost()+":"+s.getPort():
                                        "http://"+s.getHost()+":"+s.getPort())
                                    .collect(Collectors.toSet());
        this.targetUrls=newTargetUrls;
        oldTargetUrls.clear();

    }

    @Scheduled(fixedRate=10*1000) //每10秒更新一次
    public void updateTargetUrlsCache1(){
        //获取当前应用的机器列表
        Map<String,Set<String>> newTargetUrlCaches=new HashMap<>();
        discoveryClient.getServices().forEach(serviceName->{
            List<ServiceInstance> serviceInstances=discoveryClient.getInstances(serviceName);
            Set<String> newTargetUrls=serviceInstances.stream()
                    .map(s -> s.isSecure()?
                            "https://"+s.getHost()+":"+s.getPort():
                            "http://"+s.getHost()+":"+s.getPort())
                    .collect(Collectors.toSet());
            newTargetUrlCaches.put(serviceName,newTargetUrls);
        });
        this.targetUrlCaches=newTargetUrlCaches;

    }
    @GetMapping("/invoke/{serviceName}/say")
    public String invokeSay(@PathVariable String serviceName,
                            @RequestParam String message){
        return restTemplate.getForObject("/"+serviceName+"/say?message="+message,String.class);
    }
    @Bean
    private RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
