package com.sonicge.context.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.PropertyValues;
import com.sonicge.beans.config.InstantiationAwareBeanPostProcessor;
import com.sonicge.beans.factory.BeanFactory;
import com.sonicge.beans.factory.BeanFactoryAware;
import com.sonicge.beans.factory.ConfigurableListableBeanFactory;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) {
        //处理@Value注解,这个注解是在属性上面添加的，而不是在类上
        Class<?> clazz = bean.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field field : declaredFields){
            //遍历所有的属性，判断是否添加了Value注解
            Value annotation = field.getAnnotation(Value.class);
            if(annotation != null){
                String value = annotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean,field.getName(),value);
            }
        }

        //todo 处理Autowired注解

        return pvs;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
