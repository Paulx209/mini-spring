package com.sonicge.context.annotation;

import cn.hutool.core.bean.BeanUtil;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.PropertyValues;
import com.sonicge.beans.config.InstantiationAwareBeanPostProcessor;
import com.sonicge.beans.factory.BeanFactory;
import com.sonicge.beans.factory.BeanFactoryAware;
import com.sonicge.beans.factory.ConfigurableListableBeanFactory;
import com.sonicge.core.convert.ConversionService;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) {
        //处理@Value注解,这个注解是在属性上面添加的，用来处理简单类型的属性
        Class<?> clazz = bean.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            //遍历所有的属性，判断是否添加了Value注解
            Value annotation = field.getAnnotation(Value.class);
            if (annotation != null) {
                Object value = annotation.value();
                value = beanFactory.resolveEmbeddedValue((String) value);

                //类型转换
                Class<?> sourceType = value.getClass();
                Class<?> targetType = field.getType();
                ConversionService conversionService = this.beanFactory.getConversionService();
                if (conversionService != null) {
                    if (conversionService.canConvert(sourceType, targetType)) {
                        value = conversionService.convert(value, targetType);
                    }
                }
                BeanUtil.setFieldValue(bean,field.getName(),value);
            }
        }

        //todo 处理Autowired注解 也是在属性上 一般是用来处理ref类型的
        for (Field field : declaredFields) {
            String refBeanName = null;
            Object targetBean = null;
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {
                Class<?> fieldType = field.getType();
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                if (qualifier != null) {
                    refBeanName = qualifier.value();
                    targetBean = beanFactory.getBean(refBeanName, fieldType);
                } else {
                    targetBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), targetBean);
            }
        }


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

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }
}
