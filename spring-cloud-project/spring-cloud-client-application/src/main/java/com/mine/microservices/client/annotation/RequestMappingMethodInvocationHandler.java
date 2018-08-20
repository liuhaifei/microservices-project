package com.mine.microservices.client.annotation;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName RequestMappingMethodInvocationHandler
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/17 14:56
 * @Version 1.0
 **/
public class RequestMappingMethodInvocationHandler implements InvocationHandler {

    private String serviceName;
    private BeanFactory beanFactory;

    private final ParameterNameDiscoverer parameterNameDiscoverer
            = new DefaultParameterNameDiscoverer();

    public RequestMappingMethodInvocationHandler(String serviceName, BeanFactory beanFactory) {
        this.serviceName=serviceName;
        this.beanFactory=beanFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //过滤 @RequestMapping
        GetMapping getMapping= AnnotationUtils.findAnnotation(method,GetMapping.class);
        if(getMapping!=null){
            String[] uri=getMapping.value();
            StringBuilder urlBuilder=new StringBuilder("http://")
                                            .append(serviceName)
                                            .append("/")
                                            .append(uri[0]);
            //获取方法参数数量
            int count=method.getParameterCount();
            //方法参数是有序 FIXME JVM虚拟机方法签名不包含参数名称
            String[] paramNames=parameterNameDiscoverer.getParameterNames(method);
            //方法参数类型集合
            Class<?>[] paramTypes=method.getParameterTypes();
            Annotation[][] annotations=method.getParameterAnnotations();
            //参数
            StringBuilder queryStringBuilder=new StringBuilder();
            for(int i=0;i<count;i++){
                Annotation[] paramAnnotation=annotations[i];
                Class<?> paramType=paramTypes[i];
                RequestParam requestParam=(RequestParam)paramAnnotation[0];
                if(requestParam!=null){
                    //取不到
//                    String paramName=paramNames[i];
                    String paramName="";
                    String requestParamName= StringUtils.hasText(requestParam.value())?
                                                requestParam.value():paramName;
                    String requestParamValue=String.class.equals(paramType)
                                            ?(String)args[i]:String.valueOf(args[i]);
                    //uri?name=x&pwd=x
                    queryStringBuilder.append("&").append(requestParamName).append("=")
                                      .append(requestParamValue);
                }
            }
            String queryString =queryStringBuilder.toString();
            if(StringUtils.hasText(queryString)){
                urlBuilder.append("?").append(queryString);
            }
            //http://${serviceName}/${uri}?${queryString}
            String url=urlBuilder.toString();

            //获取RestTemplate Bean名称为"lbRestTempleate"
            RestTemplate restTemplate=beanFactory.getBean("lbRestTemplate",RestTemplate.class);

            return restTemplate.getForObject(url,method.getReturnType());
        }
        return null;
    }
}
