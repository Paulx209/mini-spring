package com.sonicge.beans.factory;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.AutowireCapableBeanFactory;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    /**
     * 根据名称查找BeanDefinition
     *
     * @param beanName
     * @return
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;


}
