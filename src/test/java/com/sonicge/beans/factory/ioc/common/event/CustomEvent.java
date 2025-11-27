package com.sonicge.beans.factory.ioc.common.event;


import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.event.ApplicationContextEvent;

/**
 * @author derekyi
 * @date 2020/12/5
 */
public class CustomEvent extends ApplicationContextEvent {

	public CustomEvent(ApplicationContext source) {
		super(source);
	}
}