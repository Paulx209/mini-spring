package com.sonicge.beans.event;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.context.ApplicationEvent;
import com.sonicge.beans.context.ApplicationEventPublisher;
import com.sonicge.beans.context.ApplicationListener;
import com.sonicge.beans.factory.BeanFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportEvent(listener,event)){
                //如果当前的事件恰好我们listener监听的事件，那么就直接执行！
                listener.onApplicationEvent(event);
            }
        }
    }

    protected boolean supportEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        //获取到实现的第一个接口的类型
        Type type = applicationListener.getClass().getGenericInterfaces()[0];
        //获取到接口中的第一个泛型
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        String typeName = typeArgument.getTypeName();
        //泛型类
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + typeName);
        }
        //判断Listener监听的事件类型 和 传入的event事件 类型是否一致！
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
