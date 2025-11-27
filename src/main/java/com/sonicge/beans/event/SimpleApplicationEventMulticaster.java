package com.sonicge.beans.event;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.context.ApplicationEvent;
import com.sonicge.beans.context.ApplicationListener;
import com.sonicge.beans.factory.BeanFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory){
        setBeanFactory(beanFactory);
    }
    @Override
    public void multicastEvent(ApplicationEvent event) {
        applicationListeners.forEach(listener -> {
            if (supportsEvent(listener,event)){
                listener.onApplicationEvent(event);
            }
        });
    }

    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> listener, ApplicationEvent event) {
        Type type = listener.getClass().getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        return  clazz.isAssignableFrom(event.getClass());
    }

}
