package com.mine.microservices.springDemo;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * @ClassName FilterTestCglib
 * @Description TODO
 * @Author 刘海飞
 * @Date 2019/1/8 15:53
 * @Version 1.0
 **/
public class FilterTestCglib {

    public static void main(String[] args) {
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(TargetObject.class);

        /**
         * (1)callback1：方法拦截器
         (2)NoOp.INSTANCE：这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
         (3)FixedValue：表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。

         */
        TargetMethodCallbackFilter filter=new TargetMethodCallbackFilter();
        Callback noOp= NoOp.INSTANCE;
        Callback callback=new TargetInterceptor();
        Callback fixed=new TargetResultFixed();

        Callback[] callbacks=new Callback[]{callback,fixed,noOp};
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(filter);

        TargetObject targetObject=(TargetObject)enhancer.create();
        System.out.println(targetObject);
        System.out.println(targetObject.method1("mmm"));
        System.out.println(targetObject.method2(100));
        System.out.println(targetObject.method3(200));
    }
}
