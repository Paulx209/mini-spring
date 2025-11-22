package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;

    /**
     * 创建beanFactory，并加载BeanDefinition
     *
     * @throws BeansException
     */
    @Override
    protected final void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 创建bean工厂
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
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
