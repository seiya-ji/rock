/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.component;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.onezhier.rock.framework.component.ComponentService;
import com.onezhier.rock.framework.component.ComponentServiceI;


/**
 * <p>Title: ComponentBootstrap </p>
 * <p>Description: 组件启动器</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class ComponentBootstrap implements ApplicationContextAware {

    @Resource
    private ComponentRegister componentRegister;

    private ApplicationContext applicationContext;

    @PostConstruct
    public void init(){
        Map<String, Object> componentServiceBeans = applicationContext.getBeansWithAnnotation(ComponentService.class);
        componentServiceBeans.values().forEach(
        		componentService -> componentRegister.doRegistration((ComponentServiceI<?,?>) componentService)
        );
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
