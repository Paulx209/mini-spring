package com.sonicge.beans.context;

import com.sonicge.beans.BeansException;

public class ApplicationContextException extends BeansException {

    public ApplicationContextException(String message) {
        super(message);
    }

    public ApplicationContextException(String message,Throwable throwable){
        super(message,throwable);
    }
}
