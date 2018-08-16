package com.mine.microservices.client.controller;

import com.mine.microservices.client.annotation.CustomizedLoadBalanced;
import com.mine.microservices.client.loadbalance.LoadBanlancedRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
@RestController
public class ClientController {

    //自定义restTemplate
    @Autowired
    @CustomizedLoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    @LoadBalanced
    private RestTemplate lbRestTempleate;

    private String currentServiceName;

//    @Scheduled(fixedRate=10*1000) //每10秒更新一次
//    public void updateTargetUrlsCache(){
//        //获取当前应用的机器列表
//        Set<String> oldTargetUrls=this.targetUrls;
//        List<ServiceInstance> serviceInstances= discoveryClient.getInstances(currentServiceName);
//        Set<String> newTargetUrls=serviceInstances.stream()
//                                    .map(s -> s.isSecure()?
//                                        "https://"+s.getHost()+":"+s.getPort():
//                                        "http://"+s.getHost()+":"+s.getPort())
//                                    .collect(Collectors.toSet());
//        this.targetUrls=newTargetUrls;
//        oldTargetUrls.clear();
//
//    }
    @GetMapping("/invoke/{serviceName}/say")
    public String invokeSay(@PathVariable String serviceName,
                            @RequestParam String message){

        return restTemplate.getForObject("/"+serviceName+"/say?message="+message,String.class);
    }
    @GetMapping("/lb/invoke/{serviceName}/say")
    public String lbInvokeSay(@PathVariable String serviceName,
                            @RequestParam String message){

        return lbRestTempleate.getForObject("http://"+serviceName+"/say?message="+message,String.class);
    }

    @Bean
    @Autowired
    @CustomizedLoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public RestTemplate lbRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ClientHttpRequestInterceptor interceptor(){
        return new LoadBanlancedRequestInterceptor();
    }

    @Bean
    @Autowired
    public Object customizer(@CustomizedLoadBalanced Collection<RestTemplate> restTemplates,
                             ClientHttpRequestInterceptor interceptor) {
        restTemplates.forEach(r -> {
            r.setInterceptors(Arrays.asList(interceptor));
        });
        return new Object();
    }
}
