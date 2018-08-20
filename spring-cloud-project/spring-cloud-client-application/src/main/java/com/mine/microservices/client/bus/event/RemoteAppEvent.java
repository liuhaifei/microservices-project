package com.mine.microservices.client.bus.event;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName RemoteAppEvent
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/20 14:00
 * @Version 1.0
 **/
public class RemoteAppEvent extends ApplicationEvent {

    private String type;

    private final String appName;

    private final boolean isCluster;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     * @param appName
     * @param isCluster
     */
    public RemoteAppEvent(Object source, String appName, boolean isCluster) {
        super(source);
        this.appName = appName;
        this.isCluster = isCluster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppName() {
        return appName;
    }

    public boolean isCluster() {
        return isCluster;
    }
}
