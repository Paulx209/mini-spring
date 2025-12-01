package com.sonicge.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathScanningCandidateComponentProvider {

    /**
     * 根据basePackage包扫描路径来加载BeanDefinition?
     * @param basePackage
     * @return
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage){
        LinkedHashSet<BeanDefinition> candidates = new LinkedHashSet<>();
        //扫描带有xxx注解的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        //给所有的类创建BeanDefinition
        for(Class<?> clazz:classes){
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }
        //返回
        return candidates;
    }
}

