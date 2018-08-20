package com.mine.microservices.client.bus.listener;

import com.mine.microservices.client.bus.event.RemoteAppEvent;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HttpRemoteAppEventListener
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 14:05
 * @Version 1.0
 **/
public class HttpRemoteAppEventListener implements SmartApplicationListener{

    private RestTemplate restTemplate=new RestTemplate();

    private DiscoveryClient discoveryClient;

    public String currentAppName;

    public void onApplicationEvent(RemoteAppEvent event){
        Object source=event.getSource();
        String appName=event.getAppName();
        List<ServiceInstance> list=discoveryClient.getInstances(appName);
        list.forEach(serviceInstance -> {
            String rootURL=serviceInstance.isSecure()?
                        "https://"+ serviceInstance.getHost()+":"+serviceInstance.getPort():
                        "http://"+ serviceInstance.getHost()+":"+serviceInstance.getPort();
            String url=rootURL+"/receive/remote/event/";

            Map<String,Object> data=new HashMap<>();
            data.put("sender",currentAppName);
            data.put("value",source);
            data.put("type",RemoteAppEvent.class.getName());

           String reponseContent= restTemplate.postForObject(url,data,String.class);
        });
    }




    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return RemoteAppEvent.class.isAssignableFrom(eventType)
                || ContextRefreshedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof RemoteAppEvent){
            onApplicationEvent((RemoteAppEvent)event);
        }else if (event instanceof ContextRefreshedEvent){
            onContextRefreshedEvent((ContextRefreshedEvent)event);
        }
    }

    private void onContextRefreshedEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext=event.getApplicationContext();
        this.discoveryClient=applicationContext.getBean(DiscoveryClient.class);
        this.currentAppName=applicationContext.getEnvironment().getProperty("spring.application.name");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
