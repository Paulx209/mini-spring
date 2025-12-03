package com.sonicge.beans.support;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanFactoryPostProcessor;
import com.sonicge.beans.config.BeanPostProcessor;
import com.sonicge.beans.context.ApplicationEvent;
import com.sonicge.beans.context.ApplicationListener;
import com.sonicge.beans.context.ConfigurableApplicationContext;
import com.sonicge.beans.event.ApplicationEventMulticaster;
import com.sonicge.beans.event.ContextClosedEvent;
import com.sonicge.beans.event.ContextRefreshedEvent;
import com.sonicge.beans.event.SimpleApplicationEventMulticaster;
import com.sonicge.beans.factory.ConfigurableListableBeanFactory;
import com.sonicge.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME ="applicationEventMulticatser";

    private ApplicationEventMulticaster applicationEventMulticaster;
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

        //初始化事件发布者
        initApplicationEventMulticaster();

        //注册事件监听器
        registerListeners();

        //提前实例化单例Bean
        beanFactory.preInstantiateSingletons();


        //发布容器刷新完成事件
        finishRefresh();
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

    /**
     * 初始化事件发布者
     */
    protected void initApplicationEventMulticaster(){
        ConfigurableListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.addSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME,applicationEventMulticaster);
    }

    /**
     * 注册事件监听器
     */
    protected  void registerListeners(){
        Map<String, ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class);
        for(ApplicationListener applicationListener : applicationListeners.values()){
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    /**
     * 发布容器刷新完成事件
     */
    protected  void finishRefresh(){
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
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
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public void close() {
        doClose();
    }

    public void doClose(){
        publishEvent(new ContextClosedEvent(this));

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
