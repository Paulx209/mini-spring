package com.sonicge.beans.support;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanFactoryPostProcessor;
import com.sonicge.beans.config.BeanPostProcessor;
import com.sonicge.beans.context.ConfigurableApplicationContext;
import com.sonicge.beans.factory.ConfigurableListableBeanFactory;
import com.sonicge.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException {
        //创建BeanFactory，并且加载BeanDefinition类
        refreshBeanFactory();

        //获取ConfigurableListableBeanFactory类
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //添加ApplicationContextAwareProcessor，让继承自ApplicationContextAware的bean能感知Bean
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //在bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);


        //BeanPostProcessor类需要提前进行实例化
        registerBeanPostProcessors(beanFactory);

        //提前实例化单例Bean
        beanFactory.preInstantiateSingletons();
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 获取ConfigurableListableBeanFactory类
     *
     * @return
     */
    public abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * bean实例化之前，执行BeanFactoryPostProcessor的postProcessBeanFactory方法！
     *
     * @param beanFactory
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessors = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors.values()) {
            //需要beanFactory参数获取到对应的BeanDefinition！
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 提前对BeanPostProcessor类进行createBean(实例化...)
     *
     * @param beanFactory
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessors = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public void close() {
        doClose();
    }

    public void doClose(){
        destroyBeans();
    }
    public void destroyBeans(){
        getBeanFactory().destroySingletons();
    }

    @Override
    public void registerShutdownHook() {
        Thread shutdownHook = new Thread(){
            public void run(){
                doClose();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }
}
