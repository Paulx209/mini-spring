package com.sonicge.beans.context;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {
    void setApplicationContext (ApplicationContext context) throws BeansException;
}
