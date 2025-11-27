package com.sonicge.aop;

public interface ClassFilter {
    /**
     * 用来匹配类的
     * @param clazz
     * @return
     */
    boolean matches(Class<?> clazz);
}
