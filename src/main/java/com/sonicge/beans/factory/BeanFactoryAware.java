package com.sonicge.beans.factory;

import com.sonicge.beans.BeansException;

public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
