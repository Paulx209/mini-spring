package com.sonicge.beans.config;

import com.sonicge.beans.PropertyValues;

import java.util.Objects;

/**
 * BeanDefinition实例保存Bean的信息，包括class信息、方法构造参数、是否为单例等。此处简化只包含class类型。
 */
public class BeanDefinition {

    public static String SCOPE_SINGLETON = "singleton";

    public static String SCOPE_PROTOTYPE = "prototype";

    //Bean的类
    private Class beanClass;

    //Bean的属性
    private PropertyValues propertyValues;

    //初始化方法名
    private String initMethodName;

    //销毁方法名
    private String destroyMethodName;

    //实例范围
    private String scope = SCOPE_SINGLETON;

    //单实例
    private boolean singleton = true;

    //多实例
    private boolean prototype = false;


    public BeanDefinition() {
    }

    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
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

    public static String getScopeSingleton() {
        return SCOPE_SINGLETON;
    }

    public static void setScopeSingleton(String scopeSingleton) {
        SCOPE_SINGLETON = scopeSingleton;
    }

    public static String getScopePrototype() {
        return SCOPE_PROTOTYPE;
    }

    public static void setScopePrototype(String scopePrototype) {
        SCOPE_PROTOTYPE = scopePrototype;
    }


    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setSingleton(boolean singleton){
        this.singleton = singleton;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setPrototype(boolean prototype){
        this.prototype = prototype;
    }

    public boolean isPrototype() {
        return prototype;
    }

    @Override
    public boolean equals(Object obj) {
        //1.如果两个的地址相同的话 返回true
        if(this == obj) return true;
        //2.如果obj为null 或者 类型不同 返回false
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        //3.类型相同的话，进行转换
        BeanDefinition that = (BeanDefinition)obj;
        //4.判断beanClass是否相同
        return beanClass.equals(that.beanClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanClass);
    }
}
