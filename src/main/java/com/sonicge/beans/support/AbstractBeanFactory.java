package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.BeanPostProcessor;
import com.sonicge.beans.config.ConfigurableBeanFactory;
import com.sonicge.beans.factory.FactoryBean;
import com.sonicge.util.StringValueResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private final Map<String, Object> factoryBeanObjectCache = new HashMap<>();

    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<StringValueResolver>();

    @Override
    public Object getBean(String name) throws BeansException {
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            //可能存在特殊的bean
            return getObjectForBeanInstance(sharedInstance, name);
        }
        //如果BeanFactory中不存在bean的话，创建一个新的bean。
        Object newBean = createBean(name, getBeanDefinition(name));
        return getObjectForBeanInstance(newBean, name);
    }

    /**
     * 如果是FactoryBean,从FactoryBean#getObject中创建bean
     *
     * @param beanInstance
     * @param beanName
     * @return
     */
    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        Object bean = beanInstance;
        if (beanInstance instanceof FactoryBean) {
            FactoryBean instance = (FactoryBean) beanInstance;
            try {
                if (instance.isSingleton()) {
                    //如果是单例的话
                    bean = factoryBeanObjectCache.get(beanName);
                    if (bean == null) {
                        bean = instance.getObject();
                        factoryBeanObjectCache.put(beanName, bean);
                    }
                } else {
                    //如果是非单例的话
                    bean = instance.getObject();
                }
            } catch (Exception ex) {
                throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", ex);
            }
        }
        return bean;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return ((T) getBean(name));
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        //如果有的话就覆盖
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }


    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for(StringValueResolver valueResolver : this.embeddedValueResolvers){
            result =valueResolver.resolveStringValue(result);
        }
        return result;
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}
