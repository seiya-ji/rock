/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.core.io.Resource;

import com.onezhier.rock.framework.application.ApplicationContextI;
import com.onezhier.rock.framework.component.ComponentServiceI;
import com.onezhier.rock.framework.domain.event.EventBusI;
import com.onezhier.rock.framework.domain.extension.AbstractComponentExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Title: CustomizeWebApplicationContext </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
@Slf4j
public class CustomizeWebApplicationContext extends AnnotationConfigServletWebServerApplicationContext implements ApplicationContextI{
	
	
	public CustomizeWebApplicationContext() {
		if(log.isDebugEnabled()) {
			log.debug("execute override CustomizeApplicationContext method");
		}
		
		this.setClassLoader(Thread.currentThread().getContextClassLoader());
	}
	
	@Override
	public Resource getResource(String location) {
		if(log.isDebugEnabled()) {
			log.debug("execute override getResource method");
		}
		
		Resource resource =  super.getResource(location);
		
		return resource;
	}
	
	@Override
	protected void initPropertySources() {
		if(log.isDebugEnabled()) {
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
		if(log.isDebugEnabled()) {
			log.debug("execute override postProcessBeanFactory method");
		}
		beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
			@Override
			public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
				//log.info("postProcessBeforeInitialization , beanName: "+beanName);
				return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
			}
		});
		super.postProcessBeanFactory(beanFactory);
		
		
	}
	
	@Override
	protected void onRefresh() {
		if(log.isDebugEnabled()) {
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
	public Object getService(String arg0) {
		return null;
	}

	@Override
	public EventBusI getEventBus() {
		Object bean = this.getBean(EventBusI.class);
		return (bean instanceof EventBusI)?(EventBusI)bean:null;
	}

	@Override
	public AbstractComponentExecutor getExtendPointExecutor() {
		Object bean = this.getBean(AbstractComponentExecutor.class);
		return (bean instanceof AbstractComponentExecutor)?(AbstractComponentExecutor)bean:null;
	}
	
	
	
	
	
 	
 
}
