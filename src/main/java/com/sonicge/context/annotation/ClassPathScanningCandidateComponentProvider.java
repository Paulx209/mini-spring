package com.sonicge.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> beanDefinitionSet = new HashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinitionSet.add(beanDefinition);
        }
        return beanDefinitionSet;
    }
}
