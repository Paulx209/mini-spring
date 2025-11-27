package com.sonicge.beans.event;

import com.sonicge.beans.context.ApplicationEvent;
import com.sonicge.beans.context.ApplicationListener;

public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 传播/发送 事件
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
