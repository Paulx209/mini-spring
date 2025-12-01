package com.sonicge.beans.config;

import com.sonicge.beans.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    /**
     * 在bean实例化之前执行
     * @param beanClass bean名称
     * @param beanName  beanName
     * @return
     * @throws BeansException 异常处理
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass,String beanName) throws BeansException;
}
