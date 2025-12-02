package com.sonicge.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.support.BeanDefinitionRegistry;
import com.sonicge.stereotype.Component;

import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner (BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    /**
     * 扫描根路径
     * @param basePackages
     */
    public void doScan(String ... basePackages){
        for(String basePackage : basePackages){
            Set<BeanDefinition> candidateComponents = findCandidateComponents(basePackage);
            for(BeanDefinition beanDefinition : candidateComponents){
                //解析bean的作用域
                String scopeValue = resolveBeanScope(beanDefinition);
                beanDefinition.setScope(scopeValue);
                beanDefinition.setSingleton("singleton".equals(scopeValue));
                beanDefinition.setPrototype(!("singleton".equals(scopeValue)));
                //生成bean的名称
                String beanName = determineBeanName(beanDefinition);
                //注册beanDefinition
                registry.registerBeanDefinition(beanName,beanDefinition);
            }
        }
    }

    /**
     * 获取Bean的作用域
     * @param beanDefinition
     * @return
     */
    private String resolveBeanScope(BeanDefinition beanDefinition){
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scopeAnnotation = beanClass.getAnnotation(Scope.class);
        String scopeValue = scopeAnnotation.values();
        if(StrUtil.isEmpty(scopeValue)){
            return StrUtil.EMPTY;
        }
        return scopeValue;
    }

    /**
     * 生成bean的名称  @Component(value属性)
     * @param beanDefinition
     * @return
     */
    private String determineBeanName(BeanDefinition beanDefinition){
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String beanName = component.value();
        //如果没有加value属性的话，默认为类名小写
        if(StrUtil.isEmpty(beanName)){
            return StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return beanName;
    }
}


