package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.core.io.DefaultResourceLoader;
import com.sonicge.core.io.ResourceLoader;

public abstract class AbstracteBeanDefinitionReader implements BeanDefinitionReader {
    public final BeanDefinitionRegistry registry;

    private  ResourceLoader resourceLoader;

    public AbstracteBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
        this.resourceLoader = new DefaultResourceLoader();
    }


    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }


    @Override
    public void loadBeanDefinitions(String[] locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }


}
