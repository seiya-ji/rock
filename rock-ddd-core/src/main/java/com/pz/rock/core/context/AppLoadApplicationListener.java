package com.pz.rock.core.context;

import org.springframework.context.ApplicationListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppLoadApplicationListener implements ApplicationListener<AppLoadApplicationEvent> {
	
	@Override
	public void onApplicationEvent(AppLoadApplicationEvent event) {
		if(log.isDebugEnabled()) {
			log.debug(" invoke app load application listener ... ,event:"+event);
		}
		
		if(event.getCallback()!=null) {
			event.getCallback().apply();
		}
		
		
	}

}
