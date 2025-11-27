package com.sonicge.beans.event;

import com.sonicge.beans.context.ApplicationContext;

public class ContextClosedEvent  extends ApplicationContextEvent{
    public ContextClosedEvent(ApplicationContext source) {
        super(source);
    }
}
