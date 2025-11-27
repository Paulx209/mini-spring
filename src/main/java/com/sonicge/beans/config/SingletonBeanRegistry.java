package com.sonicge.beans.config;

/**
 * 单例注册表; 这个接口不仅仅有getSingleton方法，后续还会继续扩展  11.20s
 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);

    void addSingleton(String beanName,Object singletonObject);
}
