/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.application.domain.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onezhier.rock.client.Response;
import com.onezhier.rock.client.event.Event;
import com.onezhier.rock.common.util.LoggerUtil;
import com.onezhier.rock.exception.BaseException;
import com.onezhier.rock.exception.ErrorCode;
import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.domain.event.EventBusI;
import com.onezhier.rock.framework.domain.event.EventHandlerI;

import lombok.extern.slf4j.Slf4j;


/**
 * <p>Title: EventBus </p>
 * <p>Description: 事件总线</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
@Component
@Slf4j
public class EventBus implements EventBusI{

    /**
     * 默认线程池
     *     如果处理器无定制线程池，则使用此默认
     */
    ExecutorService defaultExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1,
                                   Runtime.getRuntime().availableProcessors() + 1,
                                   0L,TimeUnit.MILLISECONDS,
                           new LinkedBlockingQueue<Runnable>(1000));

    @Autowired
    private EventHub eventHub;

    @SuppressWarnings("unchecked")
    @Override
    public Response fire(Event event) {
        Response response = null;
        EventHandlerI eventHandlerI = null;
        try {
            eventHandlerI = eventHub.getEventHandler(event.getClass()).get(0);
            response = eventHandlerI.execute(event);
        }catch (Exception exception) {
            response = handleException(eventHandlerI, response, exception);
        }
        return response;
    }

    @Override
    public void fireAll(Event event){
        eventHub.getEventHandler(event.getClass()).stream().map(p->{
            Response response = null;
            try {
                response = p.execute(event);
            }catch (Exception exception) {
                response = handleException(p, response, exception);
            }
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public void asyncFire(Event event){
        eventHub.getEventHandler(event.getClass()).parallelStream().map(p->{
            Response response = null;
            try {
                if(null != p.getExecutor()){
                    p.getExecutor().submit(()->p.execute(event));
                }else{
                    defaultExecutor.submit(()->p.execute(event));
                }
            }catch (Exception exception) {
                response = handleException(p, response, exception);
            }
            return response;
        }).collect(Collectors.toList());
    }

    private Response handleException(EventHandlerI handler, Response response, Exception exception) {
    	LoggerUtil.error(log, exception.getMessage(), exception);
        Class responseClz = eventHub.getResponseRepository().get(handler.getClass());
        try {
            response = (Response) responseClz.newInstance();
        } catch (Exception e) {
        	LoggerUtil.error(log, e.getMessage(), e);
            throw new SysException(e.getMessage());
        }
        if (exception instanceof BaseException) {
            String errCode = ((BaseException) exception).getErrCode();
            response.setErrCode(errCode);
        }
        else {
            response.setErrCode(ErrorCode.DEFAULT_SYS_ERR_CODE);
        }
        response.setErrMessage(exception.getMessage());
        return response;
    }
}
