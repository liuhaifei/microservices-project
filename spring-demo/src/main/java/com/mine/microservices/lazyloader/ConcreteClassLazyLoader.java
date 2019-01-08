package com.mine.microservices.lazyloader;

import com.mine.microservices.springDemo.TargetObject;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.LazyLoader;

/**
 * @ClassName ConcreteClassLazyLoader
 * @Description TODO
 * @Author 刘海飞
 * @Date 2019/1/8 16:34
 * @Version 1.0
 **/
public class ConcreteClassLazyLoader implements LazyLoader {
    @Override
    public Object loadObject() throws Exception {
        System.out.println("before lazyLoader...");
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("zghw");
        propertyBean.setValue(new TargetObject());
        System.out.println("after lazyLoader...");
        return propertyBean;

    }
}
