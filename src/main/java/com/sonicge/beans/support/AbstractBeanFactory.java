package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.ConfigurableBeanFactory;
import com.sonicge.beans.factory.BeanFactory;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if(bean!=null){
            return bean;
        }
        //如果BeanFactory中不存在bean的话，创建一个新的bean。
        Object newBean = createBean(name, getBeanDefinition(name));
        return newBean;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return ((T)getBean(name));
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}
