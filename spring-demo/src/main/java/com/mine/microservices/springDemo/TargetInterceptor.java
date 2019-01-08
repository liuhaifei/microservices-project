package com.mine.microservices.springDemo;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName TargetInterceptor
 * @Description TODO
 * @Author 刘海飞
 * @Date 2019/1/8 14:57
 * @Version 1.0
 **/
public class TargetInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("调用前");
        Object result= methodProxy.invokeSuper(o,objects);
        System.out.println("调用后");
        return result;
    }
}
