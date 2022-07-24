/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.application.domain.event.registe;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.onezhier.rock.framework.domain.event.EventHandler;
import com.onezhier.rock.framework.domain.event.EventHandlerI;
import com.pz.rock.core.plugin.application.domain.event.EventRegister;



/**
 * <p>Title: ExtensionBootstrap </p>
 * <p>Description: 事件启动器</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class EventBootstrap implements ApplicationContextAware {

    @Resource
    private EventRegister eventRegister;

    private ApplicationContext applicationContext;

    @PostConstruct
    public void init(){
    	 Map<String, Object> eventHandlerBeans = applicationContext.getBeansWithAnnotation(EventHandler.class);
    	    eventHandlerBeans.values().forEach(
    	            eventHandler -> eventRegister.doRegistration((EventHandlerI) eventHandler)
    	    );
    }

   
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
