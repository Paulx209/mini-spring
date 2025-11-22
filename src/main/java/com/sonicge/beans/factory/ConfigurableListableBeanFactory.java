package com.sonicge.beans.factory;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanDefinition;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory{
    /**
     * 根据名称查找BeanDefinition
     * @param beanName
     * @return
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 提前实例化所有单例实例
     * @throws BeansException
     */
    void preInstantiateSingletons() throws BeansException;
}
