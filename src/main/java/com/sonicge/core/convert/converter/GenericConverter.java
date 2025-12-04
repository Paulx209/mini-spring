package com.sonicge.core.convert.converter;

import java.util.Objects;
import java.util.Set;

public interface GenericConverter {

    Set<ConvertiblePair> getConvertibleTypes();

    /**
     * 可以将sourceType类型的source变量 转换为 targetType类型的值
     * @param source
     * @param sourceType
     * @param targetType
     * @return
     */
    Object convert(Object source,Class<?> sourceType ,Class<?> targetType);

    public static final class ConvertiblePair{
        private final Class<?> sourceType;

        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType,Class<?> targetType){
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType(){
            return this.sourceType;
        }

        public Class<?> getTargetType(){
            return this.targetType;
        }

        @Override
        public boolean equals(Object obj) {
            //1.如果地址相同的话,直接返回
            if(obj == this){
                return true;
            }
            //2.如果地址不相同，比较他们的类型
            if(obj == null || obj.getClass() != ConvertiblePair.class){
                return false;
            }
            //3.类型转换
            ConvertiblePair genericConverter = (ConvertiblePair) obj;
            return this.sourceType.equals(genericConverter.getSourceType()) && this.targetType.equals(genericConverter.getTargetType());
        }

        @Override
        public int hashCode() {
            return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
        }
    }

}
