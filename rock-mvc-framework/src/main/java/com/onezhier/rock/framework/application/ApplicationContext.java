/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application;

import com.onezhier.rock.framework.component.ComponentServiceI;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * <p>Title: ApplicationContextHolder </p>
 * <p>Description: 应用上下文 </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Data
@RequiredArgsConstructor
public class ApplicationContext  {
	
//	private EventBusI eventBus;
//	
//	private AbstractComponentExecutor componentExecutor;
	
	@NonNull
	private AbstractApplicationContextProxy proxy;
	
	public Object getService(String serviceName) {
		return this.proxy.getBean(serviceName);
	}
	
	 public  <T> T getBean(Class<T> targetClz) {
		 return this.proxy.getBean(targetClz);
	 }
	 
	 public ComponentServiceI<?, ?>  getComponentService(String componentName){
		 return (ComponentServiceI<?, ?>)this.proxy.getBean(componentName);
	 }
}
