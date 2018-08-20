package com.mine.microservices.server.bus.listener;


import com.mine.microservices.server.bus.event.SenderRemoteAppEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;


/**
 * @ClassName HttpRemoteAppEventListener
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 14:05
 * @Version 1.0
 **/
public class HttpRemoteAppReceiverListener implements ApplicationListener<SenderRemoteAppEvent>{

    private Logger logger= LoggerFactory.getLogger(HttpRemoteAppReceiverListener.class);

    @Override
    public void onApplicationEvent(SenderRemoteAppEvent event) {
        logger.info("接受到事件源："+event.getClass().getSimpleName()+",来自应用："+event.getSender());
    }
}
