package com.sonicge.beans.factory;

import com.sonicge.beans.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {
    /**
     * 返回指定类型的所有实例  (不再是单例bean)
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */

    <T> Map<String, T> getBeanOfType(Class<T> type) throws BeansException;


    /**
     * 返回定义的所有bean名称
     * @return
     */
    String[] getBeanDefinitionNames();
}
