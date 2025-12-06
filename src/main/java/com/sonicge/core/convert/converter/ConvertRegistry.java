package com.sonicge.core.convert.converter;

public interface ConvertRegistry {
    /**
     * 添加Converter到Map中，每一个Pair对应一个转换器！
     * @param converter
     */
    void addConverter(Converter<?,?> converter);


    /**
     * 添加ConverterFactory
     * @param converterFactory
     */
    void addConverterFactory(ConverterFactory<?,?> converterFactory);


    /**
     * 添加GenericConverter
     * @param genericConverter
     */
    void addGenericConverter(GenericConverter genericConverter);
}
