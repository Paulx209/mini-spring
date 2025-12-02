package com.sonicge.aop.framework.autoProxy;

import com.sonicge.aop.*;
import com.sonicge.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.sonicge.aop.framework.ProxyFactory;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.PropertyValues;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.InstantiationAwareBeanPostProcessor;
import com.sonicge.beans.factory.BeanFactory;
import com.sonicge.beans.factory.BeanFactoryAware;
import com.sonicge.beans.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

import com.sonicge.aop.PointCut;

public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        //1.避免死循环
        if (isInfrastructureClass(beanClass)) {
            return null;
        }
        //2.获取AspectJExpressionPointcutAdvisor类
        Collection<AspectJExpressionPointcutAdvisor> aspectJExpressionPointcutAdvisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        try {
            for (AspectJExpressionPointcutAdvisor advisor : aspectJExpressionPointcutAdvisors) {
                //遍历所有的advisor，获取其中的属性
                MethodInterceptor methodInterceptor = (MethodInterceptor) advisor.getAdvice();
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                MethodMatcher methodMatcher = advisor.getPointcut().getMethodMatcher();

                if (classFilter.matches(beanClass)) {
                    //草，之前直接传了个Class对象，搞错了。。
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                    Object bean = beanFactory.getInstantiationStrategy().instantiate(beanDefinition);
                    //如果当前的类是需要被代理的话
                    AdvicedSupport advicedSupport = new AdvicedSupport();
                    advicedSupport.setMethodInterceptor(methodInterceptor);
                    advicedSupport.setTargetSource(new TargetSource(bean));
                    advicedSupport.setMethodMatcher(methodMatcher);

                    ProxyFactory proxyFactory = new ProxyFactory(advicedSupport);
                    return proxyFactory.getProxy();
                }
            }
        } catch (Exception e) {
            throw new BeansException("Error create proxy bean for: " +beanName,e);
        }
        return null;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || PointCut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) {
        return pvs;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
