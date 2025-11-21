package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * Bean实例化普通的实现
 * 根据反射机制 --- 无参构造函数
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class clazz = beanDefinition.getBeanClass();
        try {
            Constructor declaredConstructor = clazz.getDeclaredConstructor();
            return declaredConstructor.newInstance();
        } catch (Exception e) {
            throw new BeansException("Bean实例化失败："+ clazz.getName() + e);
        }
    }
}
