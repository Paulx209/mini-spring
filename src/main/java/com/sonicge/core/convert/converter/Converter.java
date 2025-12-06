package com.sonicge.core.convert.converter;

public interface Converter<S,T> {
    /**
     * 转换数据作用：提供将S类型的数据转换为T类型
     * @param data
     * @return
     */
    T convert(S data);
}
