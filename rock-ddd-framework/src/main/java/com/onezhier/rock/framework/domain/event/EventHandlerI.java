/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.domain.event;


import java.util.concurrent.ExecutorService;

import com.onezhier.rock.client.Response;
import com.onezhier.rock.client.event.Event;



/**
 * <p>Title: EventHandlerI </p>
 * <p>Description: event handler</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
public interface EventHandlerI<R extends Response, E extends Event> {

    default public ExecutorService getExecutor(){
        return null;
    }

    public R execute(E e);
}
