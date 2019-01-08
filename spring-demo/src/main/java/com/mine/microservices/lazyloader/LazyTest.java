package com.mine.microservices.lazyloader;

/**
 * @ClassName LazyTest
 * @Description TODO
 * @Author 刘海飞
 * @Date 2019/1/8 17:24
 * @Version 1.0
 **/
public class LazyTest {

    public static void main(String[] args) {
        LazyBean lazyBean=new LazyBean("zs",20);
        System.out.println(lazyBean.getAge());
        System.out.println(lazyBean.getName());

        PropertyBean propertyBeanLazyLoder=lazyBean.getPropertyBean();
        PropertyBean propertyBeanDispatcher=lazyBean.getPropertyBeanDispatcher();

//        System.out.println(propertyBeanLazyLoder.getKey());
//        System.out.println(propertyBeanLazyLoder.getValue());

        System.out.println(propertyBeanDispatcher.getKey());
        System.out.println(propertyBeanDispatcher.getValue());
    }
}
