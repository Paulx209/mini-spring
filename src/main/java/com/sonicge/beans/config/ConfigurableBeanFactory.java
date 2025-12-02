package com.sonicge.beans.config;

import com.sonicge.beans.factory.HierarchicalBeanFactory;
import com.sonicge.util.StringValueResolver;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory,SingletonBeanRegistry{

    /**
     *
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例bean
     */
    void destroySingletons();

    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    String resolveEmbeddedValue(String value);
}
