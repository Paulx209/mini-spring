package com.sonicge.beans.config;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * 执行BeanPostProcessor的postProcessorsBeforeInitialization方法
     * 在bean执行初始化方法之前执行此方法
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean,String beanName) throws BeansException;


    /**
     * 执行BeanPostProcessor的postProcessorsAfterInitialization方法
     * 在bean执行初始化方法之后执行此方法
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorAfterInitialization(Object existingBean,String beanName) throws BeansException;
}
