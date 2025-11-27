package com.sonicge.beans.context;

import java.util.EventListener;

public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    /**
     * 监听event事件，当multicaster广播消息之后，就会执行每个listener的监听event方法。
     * @param event
     */
    void onApplicationEvent(E event);
}
