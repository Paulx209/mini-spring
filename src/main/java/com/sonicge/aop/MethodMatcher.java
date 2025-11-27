package com.sonicge.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
    /**
     *  用来匹配方法的  class + method 拼成唯一的方法
     */
    boolean matches(Method method, Class<?> targetClass);
}
