package com.mine.microservices.lazyloader;

import com.mine.microservices.springDemo.TargetObject;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Dispatcher;

/**
 * @ClassName ConcreteClassDispatcher
 * @Description TODO
 * @Author 刘海飞
 * @Date 2019/1/8 17:13
 * @Version 1.0
 **/
public class ConcreteClassDispatcher implements Dispatcher {
    @Override
    public Object loadObject() throws Exception {
        System.out.println("before Dispatcher...");
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("xxx");
        propertyBean.setValue(new TargetObject());
        System.out.println("after Dispatcher...");
        return propertyBean;

    }
}
