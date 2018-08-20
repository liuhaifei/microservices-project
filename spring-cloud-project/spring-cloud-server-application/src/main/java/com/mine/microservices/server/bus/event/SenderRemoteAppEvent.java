package com.mine.microservices.server.bus.event;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName SenderRemoteAppEvent
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 15:14
 * @Version 1.0
 **/
public class SenderRemoteAppEvent extends ApplicationEvent {

    private final String sender;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     * @param sender
     */
    public SenderRemoteAppEvent(Object source, String sender) {
        super(source);
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}
