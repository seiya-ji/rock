/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.application.domain.event;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onezhier.rock.client.event.Event;
import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.domain.event.EventHandlerI;


/**
 * <p>Title: EventRegister </p>
 * <p>Description: 事件注册器</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class EventRegister{

    @Resource
    private EventHub eventHub;
    
    public final static String EXE_METHOD = "execute";

    private Class<? extends Event> getEventFromExecutor(Class<?> eventExecutorClz) {
        Method[] methods = eventExecutorClz.getDeclaredMethods();
        for (Method method : methods) {
            if (isExecuteMethod(method)){
                return checkAndGetEventParamType(method);
            }
        }
        throw new SysException("Event param in " + eventExecutorClz + " " + EXE_METHOD
                                 + "() is not detected");
    }

    public void doRegistration(EventHandlerI eventHandler){
        Class<? extends Event> eventClz = getEventFromExecutor(eventHandler.getClass());
        eventHub.register(eventClz, eventHandler);
    }

    private boolean isExecuteMethod(Method method){
        return EXE_METHOD.equals(method.getName()) && !method.isBridge();
    }

    private Class checkAndGetEventParamType(Method method){
        Class<?>[] exeParams = method.getParameterTypes();
        if (exeParams.length == 0){
            throw new SysException("Execute method in "+method.getDeclaringClass()+" should at least have one parameter");
        }
        if(!Event.class.isAssignableFrom(exeParams[0]) ){
            throw new SysException("Execute method in "+method.getDeclaringClass()+" should be the subClass of Event");
        }
        return exeParams[0];
    }
}
