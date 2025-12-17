package com.sonicge.aop.autoProxy;

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
import java.util.HashSet;
import java.util.Set;

import com.sonicge.aop.PointCut;

public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    private Set<Object> earlyProxyReferences = new HashSet<>();

    /**
     * 初始化之后执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 检查是否已经提前进行了代理类的创建
        if(!earlyProxyReferences.contains(beanName)){
            return wrapIfNecessary(bean,beanName);
        }
        return bean;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

    /**
     * 创建代理对象方法！
     * @param bean
     * @param beanName
     * @return Object -> proxy代理对象
     */
    protected Object wrapIfNecessary(Object bean, String beanName) {
        Class<?> beanClass = bean.getClass();
        //1.避免死循环
        if (isInfrastructureClass(beanClass)) {
            return bean;
        }
        //2.获取AspectJExpressionPointcutAdvisor类
        Collection<AspectJExpressionPointcutAdvisor> aspectJExpressionPointcutAdvisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        try {
            ProxyFactory proxyFactory = new ProxyFactory();
            for (AspectJExpressionPointcutAdvisor advisor : aspectJExpressionPointcutAdvisors) {
                //遍历所有的advisor，获取其中的属性
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(beanClass)) {
                    //草，之前直接传了个Class对象，搞错了。。 应该传递是Target的实例化对象;如果每次都创建bean实例的话，可能会遇到一些问题
                    //BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                    //Object targetBean = beanFactory.getInstantiationStrategy().instantiate(beanDefinition);
                    TargetSource targetSource = new TargetSource(bean);
                    proxyFactory.setTargetSource(targetSource);
                    proxyFactory.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
                    proxyFactory.addAdvisor(advisor);
                }
            }
            if(!proxyFactory.getAdvisors().isEmpty()){
                return proxyFactory.getProxy();
            }
        } catch (Exception e) {
            throw new BeansException("Error create proxy bean for: " + beanName, e);
        }
        return bean;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || PointCut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    /**
     * 在实例化之后，applyProperties执行之前，执行！
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) {
        return pvs;
    }

    /**
     * 实例化之前执行
     *
     * @param beanClass bean名称
     * @param beanName  beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * 实例化之后执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    /**
     * 初始化之前执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
