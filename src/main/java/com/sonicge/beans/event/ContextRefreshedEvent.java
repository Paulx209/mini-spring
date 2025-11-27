package com.sonicge.beans.event;

import com.sonicge.beans.context.ApplicationContext;

public class ContextRefreshedEvent extends ApplicationContextEvent{
    public ContextRefreshedEvent(ApplicationContext source) {
        super(source);
    }
}
