package com.sonicge.core.convert.converter;

public interface ConvertRegistry {
    void addConverter(Converter<?,?> converter);

    void addConverterFactory(ConverterFactory<?,?> converterFactory);

    void addConverter(GenericConverter converter);
}
