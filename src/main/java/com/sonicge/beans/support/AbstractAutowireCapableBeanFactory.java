package com.sonicge.beans.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.PropertyValue;
import com.sonicge.beans.PropertyValues;
import com.sonicge.beans.config.*;

import com.sonicge.beans.factory.BeanFactoryAware;
import com.sonicge.beans.factory.DisposableBean;
import com.sonicge.beans.factory.InitializingBean;
import com.sonicge.core.convert.ConversionService;

import java.lang.reflect.Method;
import java.util.List;


public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        //如果Bean需要代理的话，则直接返回代理对象
        Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
        if (bean != null) {
            return bean;
        }
        //如果不需要代理的话，走普通路线
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Object proxyInstance = applyBeanPostProcessorsBeforeInitialization(beanClass, beanName);
        if (proxyInstance != null) {
            applyBeanPostProcessorAfterInitialization(proxyInstance, beanName);
        }
        return proxyInstance;
    }

    protected Object applyBeanPostProcessorsBeforeInitialization(Class beanClass, String beanName) {
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                //如果注入了InstantiationAwareBeanPostProcessor类型的bean的话
                Object proxyInstance = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (proxyInstance != null) {
                    return proxyInstance;
                }
            }
        }
        return null;
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            //1.实例化bean
            bean = createBeanInstance(beanDefinition);

            //实例化bean之后就把bean放到二级缓存中！
            earlySingletonObjects.put(beanName,bean);

            //实例化bean之后执行
            boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
            if (!continueWithPropertyPopulation) {
                return bean;
            }
            //在设置属性之前，允许BeanPostProcessor修改属性值
            applyBeanPostprocessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
            //2.为bean填充属性  （这里类似于执行bean的生命周期）
            applyPropertyValues(beanName, bean, beanDefinition);
            //3.执行bean的初始化方法和BeanPostProcessor的前置和后置处理方法
            initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("类的实例化失败...", e);
        }

        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        //只有bean设置为单例属性，才添加到单例singletonMap中
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }
        return bean;
    }


    /**
     * 实例化之后执行，如果返回false，不执行后续设置属性的逻辑
     *
     * @param beanName
     * @param bean
     * @return
     */
    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
            boolean continueWithPropertyPopulation = true;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                //默认返回为true，一般不会进去
                if (!((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessAfterInstantiation(bean, beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }


    /**
     * 在设置bean属性之前，允许BeanPostProcessor修改属性值
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    private void applyBeanPostprocessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues propertyValues = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (propertyValues != null) {
                    for (PropertyValue ps : propertyValues.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(ps);
                    }
                }
            }
        }
    }


    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (beanDefinition.isSingleton()) {
            if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
                registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
            }
        }
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
            //如果是复杂类型，需要获取到对应的beanName(BeanReference里面存储)
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                String referenceBeanName = beanReference.getBeanName();
                value = getBean(referenceBeanName);
            } else {
                //如果是简单类型的话，要进行类型转换
                Class<?> sourceType = value.getClass();
                Class<?> targetType = (Class<?>) TypeUtil.getFieldType(bean.getClass(), name);
                ConversionService conversionService = getConversionService();
                if (conversionService != null) {
                    if (conversionService.canConvert(sourceType, targetType)) {
                        value = conversionService.convert(value, targetType);
                    }
                }
            }
            try {
                //2.通过反射设置属性
                BeanUtil.setFieldValue(bean, name, value);
            } catch (Exception e) {
                throw new BeansException("属性赋值失败:" + beanName, e);
            }
        }
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        //执行BeanPostProcessor的前置处理器
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        //执行Bean的初始化方法  afterProperties -> 自定义的initMethod
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

    /**
     * 初始化方法，包括两个步骤，一个是afterProperties()方法； 另一个是自定义的InitMethodName()方法！
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @throws Exception
     */
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        //1.先执行InitializingBean接口下的afterProperties方法
        if (bean instanceof InitializingBean) {
            //如果bean是InitializingBean接口的实现类的话。
            ((InitializingBean) bean).afterPropertiesSet();
        }
        //2.再执行自定义的init-method方法
        String initMethodName = beanDefinition.getInitMethodName();
        //避免重复调用afterPropertiesSet方法(如果bean属于InitializingBean接口的实现类，并且init-method属性的方法也是afterPropertiesSet方法的话，则不执行init-method方法)
        if (StrUtil.isNotEmpty(initMethodName) && !(bean instanceof InitializingBean && (initMethodName.equals("afterPropertiesSet")))) {
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
            if (initMethod == null) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
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
