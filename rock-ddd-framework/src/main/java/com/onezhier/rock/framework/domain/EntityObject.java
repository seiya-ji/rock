/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.domain;

import java.util.Date;

import lombok.Data;

/**
 * <p>Title: EntityObject </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
@Data
public  class EntityObject {

	private Long id;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String createUserName;
	
	protected Long createUserId;
	
	protected String updateUserName;
	
	protected Long updateUserId;
	
	
	

}
