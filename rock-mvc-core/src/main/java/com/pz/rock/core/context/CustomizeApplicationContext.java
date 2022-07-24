/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import com.onezhier.rock.framework.application.ApplicationContextI;
import com.onezhier.rock.framework.component.ComponentServiceI;


import lombok.extern.slf4j.Slf4j;

/**
 * <p>Title: CustomizeApplicationContext </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
@Slf4j
public class CustomizeApplicationContext extends AnnotationConfigApplicationContext implements ApplicationContextI{
	
	
	public CustomizeApplicationContext() {
		if(log.isDebugEnabled()){
			log.debug("execute override CustomizeApplicationContext method");
		}
		
		this.setClassLoader(Thread.currentThread().getContextClassLoader());
	}
	
	@Override
	public Resource getResource(String location) {
		if(log.isDebugEnabled()){
			log.debug("execute override getResource method, location:"+location);
		}
		Resource resource =  super.getResource(location);

		
		return resource;
	}
	
	@Override
	protected void initPropertySources() {
		if(log.isDebugEnabled()){
			log.debug("execute override initPropertySources method");
		}
		super.initPropertySources();
		
	}
	
	@Override
	protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		super.registerBeanPostProcessors(beanFactory);
	}
	
	@Override
	protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		if(log.isDebugEnabled()){
			log.debug("execute override postProcessBeanFactory method");
		}
		
		beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
			@Override
			public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
				if(log.isDebugEnabled()){
					log.debug("postProcessBeforeInitialization , beanName: "+beanName+" bean:"+bean);
				}
				
				return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
			}
		});
		super.postProcessBeanFactory(beanFactory);
		
		
	}
	
	@Override
	protected void onRefresh() {
		if(log.isDebugEnabled()){
			log.debug("execute override onRefresh method");
		}
		super.onRefresh();
		
	}
	
	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		return super.getBean(requiredType);
	}
	
	@Override
	public Object getBean(String name) throws BeansException {
		return super.getBean(name);
	}

	@Override
	public ComponentServiceI<?, ?> getComponentService(String componentName) {
		Object bean = this.getBean(componentName);
		return (bean instanceof ComponentServiceI)?(ComponentServiceI<?, ?>)bean:null;
	}

	@Override
	public Object getService(String serviceName) {
		return null;
	}

//	@Override
//	public EventBusI getEventBus() {
//		Object bean = this.getBean(EventBusI.class);
//		return (bean instanceof EventBusI)?(EventBusI)bean:null;
//	}
//
//	@Override
//	public AbstractComponentExecutor getExtendPointExecutor() {
//		Object bean = this.getBean(AbstractComponentExecutor.class);
//		return (bean instanceof AbstractComponentExecutor)?(AbstractComponentExecutor)bean:null;
//	}
	
	
	
 	
 
}
