package com.sonicge.beans.context;

import java.util.EventObject;

public abstract class ApplicationEvent extends EventObject {

    /**
     * 需要传递一个事件源！
     * @param source
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
