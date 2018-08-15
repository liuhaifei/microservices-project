package com.mine.microservices.client.loadbalance;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName LoadBanlancedRequestInterceptor
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/15 14:33
 * @Version 1.0
 **/
public class LoadBanlancedRequestInterceptor implements ClientHttpRequestInterceptor {
    private volatile Map<String,Set<String>> targetUrlCaches=new HashMap<>();
    @Autowired
    private DiscoveryClient discoveryClient;

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
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // URI: "/"+serviceName+"/say?message="
        URI requestURI=request.getURI();
        String path=requestURI.getPath();
        String[] parts= StringUtils.split(path.substring(1),"/");
        String serviceName=parts[0];
        String uri=parts[1];
        //服务列表
        List<String> targetUrls=new LinkedList<>(targetUrlCaches.get(serviceName));
        int size=targetUrls.size();
        int index=new Random().nextInt(size);
        //选择一台服务器
        String targetUrl=targetUrls.get(index);
        //最终服务器
        String actualURL=targetUrl+"/"+uri+"?"+requestURI.getQuery();
        //执行请求
        System.out.println("本次请求的URL："+actualURL);

        URL url=new URL(actualURL);
        URLConnection urlConnection=url.openConnection();

        // 响应头
        HttpHeaders httpHeaders = new HttpHeaders();
        // 响应主体
        InputStream responseBody = urlConnection.getInputStream();
        return new SimpleClientHttpResponse(httpHeaders,responseBody);
    }



    private static class SimpleClientHttpResponse implements ClientHttpResponse {

        private HttpHeaders headers;

        private InputStream body;

        public SimpleClientHttpResponse(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return HttpStatus.OK;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return 200;
        }

        @Override
        public String getStatusText() throws IOException {
            return "OK";
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
