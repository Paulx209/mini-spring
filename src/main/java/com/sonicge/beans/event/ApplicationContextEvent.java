package com.sonicge.beans.event;

import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.context.ApplicationEvent;

public abstract class ApplicationContextEvent extends ApplicationEvent {

    /**
     * 需要传递一个事件源！
     *
     * @param source
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }
}
