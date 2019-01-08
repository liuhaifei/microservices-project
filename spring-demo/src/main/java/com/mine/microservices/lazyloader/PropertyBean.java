package com.mine.microservices.lazyloader;

/**
 * @ClassName PropertyBean
 * @Description TODO
 * @Author 刘海飞
 * @Date 2019/1/8 16:23
 * @Version 1.0
 **/
public class PropertyBean {

    private String key;
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PropertyBean{");
        sb.append("key='").append(key).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
