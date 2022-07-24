package com.onezhier.rock.framework.application;

import com.onezhier.rock.framework.component.ComponentServiceI;
import com.onezhier.rock.framework.domain.event.EventBusI;
import com.onezhier.rock.framework.domain.extension.AbstractComponentExecutor;

public interface ApplicationContextI {

	 public Object getService(String serviceName);
	
	 public  <T> T getBean(Class<T> targetClz) ;
	 
	 public  Object getBean(String beanName) ;
	 
	 public ComponentServiceI<?, ?>  getComponentService(String componentServiceName);
	 
	 public EventBusI getEventBus();
	 
	 public AbstractComponentExecutor getExtendPointExecutor();
}
