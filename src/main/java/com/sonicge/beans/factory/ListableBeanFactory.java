package com.sonicge.beans.factory;

import com.sonicge.beans.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {
    /**
     * 提前doCreateBean(实例化、属性赋值、初始化)某个Bean类型
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;


    /**
     * 返回定义的所有bean名称
     * @return
     */
    String[] getBeanDefinitionNames();
}
