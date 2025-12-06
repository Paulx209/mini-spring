package com.sonicge.core.convert.converter;

public interface ConverterFactory<S, R> {
    /**
     * ConverterFactory工厂，可以返回一个Convert<S,T>对象，这个T要在方法最前面进行声明 -> <T extends R>
     *
     * @param targetType
     * @param <T>
     * @return
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
