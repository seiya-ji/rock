/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.application.domain.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.onezhier.rock.client.event.Event;
import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.domain.event.EventHandlerI;


/**
 * <p>Title: EventHub </p>
 * <p>Description: 事件控制中枢</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@SuppressWarnings("rawtypes")
@Component
public class EventHub {

    private HashMap<Class, List<EventHandlerI>> eventRepository = new HashMap<>();
    
    private Map<Class, Class> responseRepository = new HashMap<>();

    public HashMap<Class, List<EventHandlerI>> getEventRepository() {
        return eventRepository;
    }

    public void setEventRepository(HashMap<Class, List<EventHandlerI>> eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Map<Class, Class> getResponseRepository() {
        return responseRepository;
    }

    public List<EventHandlerI> getEventHandler(Class eventClass) {
        List<EventHandlerI> eventHandlerIList = findHandler(eventClass);
        if (eventHandlerIList == null || eventHandlerIList.size() == 0) {
            throw new SysException(eventClass + " is not registered in eventHub, please register first");
        }
        return eventHandlerIList;
    }

    /**
     * 注册事件
     * @param eventClz
     * @param executor
     */
    public void register(Class<? extends Event> eventClz, EventHandlerI executor){
        List<EventHandlerI> eventHandlers = eventRepository.get(eventClz);
        if(eventHandlers == null){
            eventHandlers = new ArrayList<>();
            eventRepository.put(eventClz, eventHandlers);
        }
        eventHandlers.add(executor);

    }

    private List<EventHandlerI> findHandler(Class<? extends Event> eventClass){
        List<EventHandlerI> eventHandlerIList = null;
        Class cls = eventClass;
        eventHandlerIList = eventRepository.get(cls);
        if(eventHandlerIList!=null) {
        	return eventHandlerIList;
        }
       
        return this.findHandler((Class<? extends Event>) eventClass.getSuperclass());
    }

}
