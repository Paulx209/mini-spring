package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.core.io.Resource;
import com.sonicge.core.io.ResourceLoader;

public interface BeanDefinitionReader {
    //获取注册表，添加BeanDefinition
    BeanDefinitionRegistry getRegistry();

    //获取资源加载器
    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;
}
