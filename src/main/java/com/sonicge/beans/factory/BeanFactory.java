package com.sonicge.beans.factory;

import com.sonicge.beans.BeansException;

import java.util.HashMap;
import java.util.Map;

public interface BeanFactory {
    /**
     * 根据名称查找Bean
     * @param name
     * @return
     * @throws
     */
    Object getBean(String name) throws BeansException;


    /**
     * 根据名称和类型查找Bean
     * @param name
     * @param requiredType
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    /**
     * 根据类型获取Bean
     * @param requiredType
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * 判断Bean是否存在
     * @param name
     * @return
     */
    boolean containsBean(String name);

}
