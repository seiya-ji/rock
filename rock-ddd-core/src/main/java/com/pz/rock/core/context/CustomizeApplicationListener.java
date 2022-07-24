package com.pz.rock.core.context;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomizeApplicationListener implements ApplicationListener<ApplicationEvent> {
	
	private boolean servletStarted = false;
	
	private AtomicInteger servletContextInitializeCnt = new AtomicInteger(0);

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		if(event instanceof ApplicationEnvironmentPreparedEvent) {
			SpringApplication application = (SpringApplication)event.getSource();
			application.setApplicationContextClass(CustomizeWebApplicationContext.class);
		}
		
		log.debug(" invoke customize application listener ... ,event:"+event);
		if(event instanceof ServletWebServerInitializedEvent) {
			servletStarted = true;
			servletContextInitializeCnt.incrementAndGet();
		}
		
	}

}
