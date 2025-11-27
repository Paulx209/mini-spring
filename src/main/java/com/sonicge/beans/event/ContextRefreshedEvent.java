package com.sonicge.beans.event;

import com.sonicge.beans.context.ApplicationEvent;

public class ContextRefreshedEvent extends ApplicationEvent {
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
