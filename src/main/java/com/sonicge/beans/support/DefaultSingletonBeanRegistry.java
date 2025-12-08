package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.SingletonBeanRegistry;
import com.sonicge.beans.factory.DisposableBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private Map<String, Object> singletonObjects = new HashMap<>();

    protected Map<String, Object> earlySingletonObjects = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    public void registerDisposableBean(String beanName, DisposableBean disposableBean) {
        disposableBeans.put(beanName, disposableBean);
    }

    public void destroySingletons() {
        Set<String> beanNames = disposableBeans.keySet();
        for (String beanName : beanNames) {
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        //先判断一级缓存中是否存在
        Object bean = singletonObjects.get(beanName);
        if (bean == null) {
            //一级缓存中如果不存在的话，从二级缓存中查找
            bean = earlySingletonObjects.get(beanName);
        }
        return bean;
    }

    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
