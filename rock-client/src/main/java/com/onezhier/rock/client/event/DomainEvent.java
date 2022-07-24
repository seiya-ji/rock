/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.client.event;


/**
 * <p>Title: DomainEvent </p>
 * <p>
 * Domain Event (领域事件） 命名规则：实体名+动词的过去时态+Event
 * 比如CustomerCreated 表示创建完客户发送出来的领域事件
 * 比如ContactAdded 表示添加完联系人发送出来的领域事件
 * 比如OpportunityTransferred 表示机会转移完发送出来的领域事件
 * </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @since 2021/3/26 4:29 下午
 */
public abstract class DomainEvent<T> extends Event {
	
	 	private T data;
	 	
	    private String tag;

		/**
		 * @return the data
		 */
		public T getData() {
			return data;
		}

		/**
		 * @param data the data to set
		 */
		public void setData(T data) {
			this.data = data;
		}

		/**
		 * @return the tag
		 */
		public String getTag() {
			return tag;
		}

		/**
		 * @param tag the tag to set
		 */
		public void setTag(String tag) {
			this.tag = tag;
		}

}
