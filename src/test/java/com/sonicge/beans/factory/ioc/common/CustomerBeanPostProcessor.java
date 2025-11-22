package com.sonicge.beans.factory.ioc.common;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanPostProcessor;
import com.sonicge.beans.factory.ioc.bean.Car;

public class CustomerBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomerBeanPostProcessor#postProcessBeforeInitialization");
        if("car" . equals(beanName)){
            Car car = (Car) bean;
            car.setBrand("lamborghini");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomerBeanPostProcessor#postProcessBeforeInitialization");
        return bean;
    }
}
