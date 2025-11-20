package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            Class beanClass = beanDefinition.getBeanClass();
            Class<?> clazz = Class.forName(beanClass.getName());
            //获取无参构造函数
            Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
            bean = declaredConstructor.newInstance();
        } catch (Exception e) {
            throw new BeansException("类的实例化失败...",e);
        }
        addSingleton(beanName,bean);
        return bean;
    }

}
