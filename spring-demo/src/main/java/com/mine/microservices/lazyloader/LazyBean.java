package com.mine.microservices.lazyloader;

import net.sf.cglib.proxy.Enhancer;

/**
 * @ClassName LazyBean
 * @Description TODO
 * @Author 刘海飞
 * @Date 2019/1/8 16:15
 * @Version 1.0
 **/
public class LazyBean {

    private String name;
    private int age;

    private PropertyBean propertyBean;
    private PropertyBean propertyBeanDispatcher;

    public LazyBean(String name, int age) {
        this.name = name;
        this.age = age;
        this.propertyBean = createPropertyBean();
        this.propertyBeanDispatcher = createPropertyBeanDispatcher();
    }


    /**
     * 只在第一次调用时 加载一次
     */
    private PropertyBean createPropertyBean(){
        /**
         * 使用cglib进行懒加载 对需要延迟加载的对象添加代理，在获取该对象属性时先通过代理类回调方法进行对象初始化。
         * 在不需要加载该对象时，只要不去获取该对象内属性，该对象就不会被初始化了（在CGLib的实现中只要去访问该对象内属性的getter方法，
         * 就会自动触发代理类回调）。
         */
        PropertyBean pb=(PropertyBean) Enhancer.create(PropertyBean.class,
                new ConcreteClassLazyLoader());
        return pb;
    }
    /**
     * 每次调用都加载一次
     */
    private PropertyBean createPropertyBeanDispatcher(){

        PropertyBean pb=(PropertyBean) Enhancer.create(PropertyBean.class,
                new ConcreteClassDispatcher());
        return pb;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PropertyBean getPropertyBean() {
        return propertyBean;
    }

    public void setPropertyBean(PropertyBean propertyBean) {
        this.propertyBean = propertyBean;
    }

    public PropertyBean getPropertyBeanDispatcher() {
        return propertyBeanDispatcher;
    }

    public void setPropertyBeanDispatcher(PropertyBean propertyBeanDispatcher) {
        this.propertyBeanDispatcher = propertyBeanDispatcher;
    }
}
