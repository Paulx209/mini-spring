package com.sonicge.beans.config;

import cn.hutool.core.bean.BeanException;
import com.sonicge.beans.factory.ConfigurableListableBeanFactory;

/**
 * BeanFactoryPostProcessor是Spring提供的容器扩展机制，允许我们在bean实例化之前修改bean的定义信息（BeanDefinition）。
 * 重要的实现类包括：PropertyPlaceholderConfigurer、CustomEditorConfigurer类。
 * 前者的作用是用Properties文件的配置值替换xml文件中的占位符；后者的作用是实现类型的转换
 */

public interface BeanFactoryPostProcessor {

    /**
     * 在所有的BeanDefinition加载完成后，bean实例化之前，提供修改BeanDefinition属性值的机制！
     *
     * @param beanFactory
     * @throws BeanException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException;
}