package com.sonicge.beans.factory.ioc.common;

import cn.hutool.core.bean.BeanException;
import com.sonicge.beans.PropertyValue;
import com.sonicge.beans.PropertyValues;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.BeanFactoryPostProcessor;
import com.sonicge.beans.factory.ConfigurableListableBeanFactory;

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException {
        System.out.println("CustomBeanFactoryPostProcessor#postProcessBeanFactory");
        BeanDefinition peopleBeanDefinition = beanFactory.getBeanDefinition("people");
        PropertyValues propertyValues = peopleBeanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name","tony"));
    }
}
