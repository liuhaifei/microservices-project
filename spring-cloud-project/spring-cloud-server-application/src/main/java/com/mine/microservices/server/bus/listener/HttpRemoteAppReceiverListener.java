package com.mine.microservices.server.bus.listener;


import com.mine.microservices.server.bus.event.SenderRemoteAppEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * @ClassName HttpRemoteAppEventListener
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 14:05
 * @Version 1.0
 **/
@Component
public class HttpRemoteAppReceiverListener {

    private Logger logger= LoggerFactory.getLogger(HttpRemoteAppReceiverListener.class);

    @EventListener
    @Async
    public void onEvent(SenderRemoteAppEvent event) {
        logger.info("接受到事件源："+event.getClass().getSimpleName()+",来自应用："+event.getSender());
        logger.info("消息："+event.getSource().toString());
    }
}
