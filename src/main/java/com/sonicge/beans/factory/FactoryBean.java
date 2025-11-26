package com.sonicge.beans.factory;

public interface FactoryBean<T> {

    /**
     * 对bean增强
     *
     * @return
     * @throws Exception
     */
    T getObject() throws Exception;

    /**
     * 是否是单例bean
     *
     * @return
     */
    boolean isSingleton();
}
