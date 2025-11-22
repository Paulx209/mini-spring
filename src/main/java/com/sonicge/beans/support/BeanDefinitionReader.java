package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.core.io.Resource;
import com.sonicge.core.io.ResourceLoader;

public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();

    ResourceLoader  getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;
}
