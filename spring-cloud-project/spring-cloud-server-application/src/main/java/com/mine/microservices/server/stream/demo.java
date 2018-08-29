package com.mine.microservices.server.stream;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName demo
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/29 15:00
 * @Version 1.0
 **/
@Component
public class demo {

    @Scheduled(fixedRate=5000)
    public void orderMealNumClear(){
        System.out.println("132");
    }

}
