package com.sonicge.beans.config;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    /**
     * 在bean实例化之前执行
     * @param beanClass bean名称
     * @param beanName  beanName
     * @return
     * @throws BeansException 异常处理
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass,String beanName) throws BeansException;

    /**
     * 在bean实例化之后，设置属性之前执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    boolean postProcessAfterInstantiation(Object bean,String beanName) throws  BeansException;

    /**
     * 在bean属性注入前执行 通过Value注解给属性赋值！
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs,Object bean,String beanName);

    /**
     * 提前暴露bean 对象引用？
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    default Object getEarlyBeanReference(Object bean,String beanName) throws BeansException{
        return bean;
    }
}
