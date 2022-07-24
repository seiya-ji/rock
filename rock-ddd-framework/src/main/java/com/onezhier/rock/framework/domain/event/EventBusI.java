/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.domain.event;

import com.onezhier.rock.client.Response;
import com.onezhier.rock.client.event.Event;


/**
 * <p>Title: EventBusI </p>
 * <p>Description: EventBus interface</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
public interface EventBusI {

    /**
     * Send event to EventBus
     * 
     * @param event
     * @return Response
     */
    public Response fire(Event event);

    /**
     * fire all handlers which registed the event
     *
     * @param event
     * @return Response
     */
    public void fireAll(Event event);

    /**
     * Async fire all handlers
     * @param event
     */
    public void asyncFire(Event event);
}
