package com.sonicge.beans.support;

import cn.hutool.core.bean.BeanUtil;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.PropertyValue;
import com.sonicge.beans.config.AutowireCapableBeanFactory;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.BeanPostProcessor;
import com.sonicge.beans.config.BeanReference;
import com.sonicge.beans.factory.BeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static java.lang.Boolean.parseBoolean;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            //1.实例化bean
            bean = createBeanInstance(beanDefinition);
            //2.为bean填充属性  （这里类似于执行bean的生命周期）
            applyPropertyValues(beanName, bean, beanDefinition);
            //3.执行bean的初始化方法和BeanPostProcessor的前置和后置处理方法
            initializeBean(beanName,bean,beanDefinition);
        } catch (Exception e) {
            throw new BeansException("类的实例化失败...", e);
        }
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 给bean填充属性
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (PropertyValue pv : beanDefinition.getPropertyValues().getPropertyValues()) {
            //1.获取属性名 和 属性类型
            String name = pv.getName();
            Object value = pv.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                String referenceBeanName = beanReference.getBeanName();
                value = getBean(referenceBeanName);
            }
            try {
                //2.通过反射设置属性
                BeanUtil.setFieldValue(bean, name, value);
            } catch (Exception e) {
                throw new BeansException("属性赋值失败:" + beanName, e);
            }
        }
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {
        //执行BeanPostProcessor的前置处理器
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        //执行Bean的初始化方法
        invokeInitMethods(beanName, wrappedBean, beanDefinition);

        //执行BeanPostProcessor的后置处理器
        wrappedBean = applyBeanPostProcessorAfterInitialization(bean, beanName);

        return wrappedBean;

    }


    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        List<BeanPostProcessor> beanPostProcessors = this.getBeanPostProcessors();
        for (BeanPostProcessor processor : beanPostProcessors) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        List<BeanPostProcessor> beanPostProcessors = this.getBeanPostProcessors();
        for (BeanPostProcessor processor : beanPostProcessors) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {
        //todo 待实现
        System.out.println("执行bean[" + beanName + "]的初始化方法");
    }


    public Object createBeanInstance(BeanDefinition beanDefinition) {
        return instantiationStrategy.instantiate(beanDefinition);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
