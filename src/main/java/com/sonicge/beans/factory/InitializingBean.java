package com.sonicge.beans.factory;

public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
