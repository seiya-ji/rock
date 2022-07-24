/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.client.event;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <p>Title: MessageQueueEvent </p>
 * <p>
 * mq的事件
 * </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @since 2021/3/26 4:29 下午
 */
@Data
@NoArgsConstructor
public  class MessageQueueEvent<T> extends Event {
	
	private T data;
 	
	private String topic;
	
    private String tag;

	
	
  
}
