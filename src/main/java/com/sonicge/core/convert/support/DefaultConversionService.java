package com.sonicge.core.convert.support;

import com.sonicge.core.convert.converter.ConvertRegistry;

public class DefaultConversionService extends GenericConversionService {
    public DefaultConversionService(){
        addDefaultConverters(this);
    }

    public static void addDefaultConverters(ConvertRegistry convertRegistry){
        convertRegistry.addConverterFactory(new StringToNumberConverter());
    }
}
