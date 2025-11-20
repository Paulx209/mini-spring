package com.sonicge.beans.support;

import com.sonicge.beans.config.BeanDefinition;

/**
 * BeanDefinition注册表接口
 */
public interface BeanDefinitionRegistry {
    /**
     * 向注册表中注入BeanDefinition  注册表中存放注册信息！
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
