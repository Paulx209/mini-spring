package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.SingletonBeanRegistry;
import com.sonicge.beans.factory.DisposableBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private Map<String, Object> singletonObjects = new HashMap<>();

    private final Map<String,DisposableBean> disposableBeans = new HashMap<>();

    public void registerDisposableBean(String beanName,DisposableBean disposableBean){
        disposableBeans.put(beanName,disposableBean);
    }

    public void destroySingletons(){
        Set<String> beanNames = disposableBeans.keySet();
        for(String beanName : beanNames){
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
        return singletonObjects.get(beanName);
    }

    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
