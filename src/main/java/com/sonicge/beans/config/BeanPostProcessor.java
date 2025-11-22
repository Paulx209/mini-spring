package com.sonicge.beans.config;

import com.sonicge.beans.BeansException;


/**
 * 用于修改实例化之后的bean的修改扩展点。bean实例化后修改bean或替换bean。
 * BeanPostProcessor是后面实现AOP的关键。
 */
public interface BeanPostProcessor {

    /**
     * 在bean执行初始化方法之前执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws Exception
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;


    /**
     * 在bean执行初始化方法之后执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws Exception
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
