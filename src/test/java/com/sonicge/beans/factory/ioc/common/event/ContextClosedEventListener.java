package com.sonicge.beans.factory.ioc.common.event;

import com.sonicge.beans.context.ApplicationListener;
import com.sonicge.beans.event.ContextClosedEvent;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println(this.getClass().getName());
    }
}
