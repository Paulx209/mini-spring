package com.sonicge.beans.factory;

import com.sonicge.beans.BeansException;

import java.util.HashMap;
import java.util.Map;

public interface BeanFactory {
    /**
     * 获取Bean
     * @param name
     * @return
     * @throws 当bean不存在时抛出自定义异常
     */
    Object getBean(String name) throws BeansException;

}
