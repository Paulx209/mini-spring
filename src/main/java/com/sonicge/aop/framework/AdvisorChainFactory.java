package com.sonicge.aop.framework;

import com.sonicge.aop.AdvicedSupport;

import java.lang.reflect.Method;
import java.util.List;

public interface AdvisorChainFactory {
    List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvicedSupport config, Method method, Class<?> targetClass);
}
