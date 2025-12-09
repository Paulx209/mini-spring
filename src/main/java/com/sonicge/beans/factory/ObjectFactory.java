package com.sonicge.beans.factory;

import com.sonicge.beans.BeansException;

public interface ObjectFactory<T> {

    T getObject() throws BeansException;
}
