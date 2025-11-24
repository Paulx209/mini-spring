package com.sonicge.beans.config;

import com.sonicge.beans.PropertyValues;

/**
 * BeanDefinition实例保存Bean的信息，包括class信息、方法构造参数、是否为单例等。此处简化只包含class类型。
 */
public class BeanDefinition {
    //Bean的类
    private Class beanClass;

    //Bean的属性
    private PropertyValues propertyValues;

    //初始化方法名
    private String initMethodName;

    //销毁方法名
    private String destroyMethodName;

    public BeanDefinition() {
    }

    public BeanDefinition(Class beanClass) {
        this(beanClass,null);
    }

    public BeanDefinition(Class beanClass,PropertyValues propertyValues){
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
