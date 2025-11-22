package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.core.io.DefaultResourceLoader;
import com.sonicge.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{
    private BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
        this.resourceLoader = new DefaultResourceLoader();
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
        for(String location : locations){
            loadBeanDefinitions(location);
        }
    }
}
