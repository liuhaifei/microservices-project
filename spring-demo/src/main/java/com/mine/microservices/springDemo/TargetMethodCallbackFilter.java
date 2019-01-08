package com.mine.microservices.springDemo;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @ClassName TargetMethodCallbackFilter
 * @Description TODO
 * @Author 刘海飞
 * @Date 2019/1/8 15:50
 * @Version 1.0
 **/
public class TargetMethodCallbackFilter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        if("method1".equals(method.getName())){
            System.out.println("call back filter method1");
            return 0;
        }
        if("method2".equals(method.getName())){
            System.out.println("call back filter method2");
            return 1;
        }
        if("method3".equals(method.getName())){
            System.out.println("call back filter method3");
            return 2;
        }
       return 0;
    }
}
