package com.mine.microservices.client.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * @ClassName RestClientsRegistrar
 * @Description bean注册
 * @Author 刘海飞
 * @Date 2018/8/17 11:10
 * @Version 1.0
 **/
public class RestClientsRegistrar implements ImportBeanDefinitionRegistrar
                        ,EnvironmentAware,BeanFactoryAware {

    private Environment environment;

    private BeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata,
                                        BeanDefinitionRegistry registry) {
        this.registerRestFeignClients(metadata,registry);
    }
    private void registerRestFeignClients(AnnotationMetadata metadata,
                                          BeanDefinitionRegistry registry){
        ClassLoader classLoader=metadata.getClass().getClassLoader();
        //attributes ->{clients:SayingService}
        Map<String,Object> attributes=
                metadata.getAnnotationAttributes(EnableRestClient.class.getName());
        //接口对象数组
        Class<?>[] clientClasses=(Class<?>[])attributes.get("clients");
        //筛选所有接口
        Stream.of(clientClasses)
              .filter(Class::isInterface)//仅选择接口
              .filter(interfaceClass
                        -> findAnnotation(interfaceClass,RestClient.class)!=null)//仅选择标注@RestClient
              .forEach(restClientClass ->{
                  //获取@RestClient元信息
                  RestClient restClient=findAnnotation(restClientClass,RestClient.class);
                  //获取应用名称(占位符处理)
                  String serviceName=environment.resolvePlaceholders(restClient.name());
                  //RestTemplate ->serviceName/uri/param=..
                  Object proxy= Proxy.newProxyInstance(classLoader,new Class[]{restClientClass},
                          new RequestMappingMethodInvocationHandler(serviceName,beanFactory));

                  //将 @RestClient 接口代理实现注册为Bean(@Autowired)

                  String beanName="RestClient."+serviceName;
                  //注册方法一
                  registerBeanByFactoryBean(serviceName,beanName,proxy,restClientClass,registry);

                  //注册方法二
//                  if(registry instanceof SingletonBeanRegistry){
//                      SingletonBeanRegistry singletonBeanRegistry=(SingletonBeanRegistry)registry;
//                      singletonBeanRegistry.registerSingleton(beanName,proxy);
//                  }

              });
        ;
    }
    private static void registerBeanByFactoryBean(String serviceName,
                                                  String beanName,
                                                  Object proxy,
                                                  Class<?> restClintClass,
                                                  BeanDefinitionRegistry registry){
        BeanDefinitionBuilder beanDefinitionBuilder=
                        BeanDefinitionBuilder.genericBeanDefinition(RestClientClassFactoryBean.class);
        //增加第一个构造器参数引用:proxy
        beanDefinitionBuilder.addConstructorArgValue(proxy);
        //增加第二个构造器参数：restClientClass
        beanDefinitionBuilder.addConstructorArgValue(restClintClass);

        BeanDefinition beanDefinition=beanDefinitionBuilder.getBeanDefinition();
        registry.registerBeanDefinition(beanName,beanDefinition);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }

    private static class RestClientClassFactoryBean implements FactoryBean {

        private final Object proxy;

        private final Class<?> restClientClass;

        private RestClientClassFactoryBean(Object proxy, Class<?> restClientClass) {
            this.proxy = proxy;
            this.restClientClass = restClientClass;
        }

        @Nullable
        @Override
        public Object getObject() throws Exception {
            return proxy;
        }

        @Nullable
        @Override
        public Class<?> getObjectType() {
            return restClientClass;
        }
    }
}
