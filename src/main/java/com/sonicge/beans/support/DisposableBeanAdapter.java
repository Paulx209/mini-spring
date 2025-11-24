package com.sonicge.beans.support;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.factory.DisposableBean;

import java.lang.reflect.Method;

public class DisposableBeanAdapter  implements DisposableBean {
    private final Object bean;

    private final String beanName;

    private final String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if(bean instanceof DisposableBean){
            ((DisposableBean) bean).destroy();
        }
        //避免同时继承自DestroyBean，且自定义方法与DisposableBean方法同名，销毁方法出现两次的情况
        if(StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy" .equals(this.destroyMethodName))){
            Method destroyMethod = ClassUtil.getPublicMethod(bean.getClass(), this.destroyMethodName);
            if(destroyMethod == null){
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
