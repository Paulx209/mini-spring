package com.sonicge.beans.config;

/**
 * BeanDefinition实例保存Bean的信息，包括class信息、方法构造参数、是否为单例等。此处简化只包含class类型。
 */
public class BeanDefinition {
    //Bean的类
    private Class beanClass;

    public BeanDefinition() {
    }

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
