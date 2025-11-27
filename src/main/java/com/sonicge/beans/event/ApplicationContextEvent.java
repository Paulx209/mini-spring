package com.sonicge.beans.event;

import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.context.ApplicationEvent;

public abstract class ApplicationContextEvent extends ApplicationEvent {


    public ApplicationContextEvent(ApplicationContext source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }
}
