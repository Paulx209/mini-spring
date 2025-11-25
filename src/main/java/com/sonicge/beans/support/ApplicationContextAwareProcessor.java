package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanPostProcessor;
import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.context.ApplicationContextAware;

public class ApplicationContextAwareProcessor  implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ApplicationContextAware){
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
