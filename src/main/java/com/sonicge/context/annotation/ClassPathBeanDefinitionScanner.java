package com.sonicge.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.support.BeanDefinitionRegistry;
import com.sonicge.stereotype.Component;

import java.util.Set;


public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "com.sonicge.context.annotation.instantiationAwareBeanPostProcessor";

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void scan(String ... basePackages){
        for(String basePackage : basePackages){
            //扫描每一个basePackage
            Set<BeanDefinition> beanDefinitionMap = findCandidateComponents(basePackage);
            for(BeanDefinition beanDefinition : beanDefinitionMap){
                String scope = resolveBeanScope(beanDefinition);
                if(StrUtil.isNotEmpty(scope)){
                    //如果singleton的话，就为false
                    beanDefinition.setScope(scope);
                    beanDefinition.setSingleton(scope.equals("singleton"));
                    beanDefinition.setPrototype(!scope.equals("singleton"));
                }

                //注册BeanDefinition到Map中
                String beanName = determineBeanName(beanDefinition);
                registry.registerBeanDefinition(beanName, beanDefinition);
            }
        }

        registry.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME,new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    /**
     * 获取Bean的作用域 scope吗？
     * @param beanDefinition
     * @return
     */
    private String resolveBeanScope(BeanDefinition beanDefinition){
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if(scope != null){
            return scope.value();
        }

        return StrUtil.EMPTY;
    }

    /**
     * 生成bean的名称
     * @param beanDefinition
     * @return
     */
    private String determineBeanName(BeanDefinition beanDefinition){
        String value  ;
        try {
            Class<?> beanClass = beanDefinition.getBeanClass();
            //首先看注解上的value
            Component component = beanClass.getAnnotation(Component.class);
            //用户设定的beanName
            value = component.value();
            if(StrUtil.isEmpty(value)){
                //如果value为空的话,返回类名小写
                return StrUtil.lowerFirst(beanClass.getSimpleName());
            }
        } catch (Exception e) {
            throw new BeansException("生成bean名称失败",e);
        }
        return value;
    }
}
