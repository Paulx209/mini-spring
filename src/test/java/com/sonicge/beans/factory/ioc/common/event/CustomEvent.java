package com.sonicge.beans.factory.ioc.common.event;

import com.sonicge.beans.event.ApplicationContextEvent;

public class CustomEvent extends ApplicationContextEvent {
    public CustomEvent(Object source) {
        super(source);
    }
}
