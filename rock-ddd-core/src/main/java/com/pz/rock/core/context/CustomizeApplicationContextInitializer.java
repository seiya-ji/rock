package com.pz.rock.core.context;

import java.util.Map.Entry;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.onezhier.rock.framework.application.AbstractLauncheApplication;
import com.pz.rock.core.loader.CustomizeJarLauncher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomizeApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		if(log.isDebugEnabled()) {
			log.debug(" invoke customize application context initalizer ... ,applicationContext:"+applicationContext);
		}
		
		//AnnotationConfigApplicationContext  parent
		//AnnotationConfigServletWebServerApplicationContext child
		if(applicationContext instanceof AnnotationConfigServletWebServerApplicationContext) {
			
			for(Entry<Long, AbstractLauncheApplication> entry :  CustomizeJarLauncher.launcheApplicationMap.entrySet()) {
				if(applicationContext.getId().equalsIgnoreCase(entry.getValue().getApplicationName())) {
					entry.getValue().setApplicationContext(applicationContext);
					
				}
			}
		}
		applicationContext.getBeanFactory().registerSingleton("customizeApplicationContext", applicationContext);
		
		
	}

}
