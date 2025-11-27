package com.sonicge.beans.factory.ioc.common.event;

import com.sonicge.beans.context.ApplicationListener;
import com.sonicge.beans.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println(this.getClass().getName());
    }
}
