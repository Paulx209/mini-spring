package com.sonicge.beans.support;

import cn.hutool.core.bean.BeanUtil;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.PropertyValue;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.BeanReference;
import com.sonicge.beans.factory.BeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.Boolean.parseBoolean;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

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

//    protected Object convertStringToType(String stringValue,Class<?> targetType){
//        if(stringValue  == null){
//            return null;
//        }
//        // 根据目标类型进行转换
//        if (targetType == String.class) {
//            return stringValue;
//        } else if (targetType == Integer.class || targetType == int.class) {
//            return Integer.parseInt(stringValue);
//        } else if (targetType == Long.class || targetType == long.class) {
//            return Long.parseLong(stringValue);
//        } else if (targetType == Double.class || targetType == double.class) {
//            return Double.parseDouble(stringValue);
//        } else if (targetType == Float.class || targetType == float.class) {
//            return Float.parseFloat(stringValue);
//        } else if (targetType == Boolean.class || targetType == boolean.class) {
//            // 布尔类型转换，支持 "true", "false", "1", "0" 等
//            return parseBoolean(stringValue);
//        } else if (targetType == Character.class || targetType == char.class) {
//            return stringValue.charAt(0);
//        } else {
//            // 其他类型可以继续扩展，或者抛出异常
//            throw new BeansException("不支持的类型转换: " + targetType.getName());
//        }
//    }

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
