package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;

public abstract class AbstractRefreshApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;

    /**
     * 创建BeanFactory 并且 加载BeanDefinition
     *
     * @throws BeansException
     */
    protected final void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();

        loadBeanDefinition(beanFactory);

        this.beanFactory = beanFactory;
    }

    /**
     * 创建BeanFactory工厂
     *
     * @return
     */
    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载BeanDefinition
     *
     * @param beanFactory
     */

    protected abstract void loadBeanDefinition(DefaultListableBeanFactory beanFactory);


    public DefaultListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

}
