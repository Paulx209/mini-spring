package com.sonicge.beans.event;

import com.sonicge.beans.context.ApplicationEvent;

public class ContextClosedEvent extends ApplicationEvent {

    /**
     * 需要传递一个事件源！
     *
     * @param source
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
