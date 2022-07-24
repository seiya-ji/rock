/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.dal.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.onezhier.rock.protocol.annotation.Attribute;
import com.onezhier.rock.protocol.annotation.CreateType;

import lombok.Data;

/**
 * <p>Title: DO </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
@MappedSuperclass
@Data
public class DO implements Serializable {
	


	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	protected Long id;
	
	@Attribute(alias = "创建时间",comment = "记录创建的时间",classify = CreateType.DEFAULT,notNull = true)
	protected Date createTime = new Date();
	
	@Attribute(alias = "最后更新时间",comment = "记录最后更新时间",classify = CreateType.DEFAULT,notNull = true)
	protected Date updateTime = new Date();
	
	@Attribute(alias = "创建用户姓名",comment = "记录创建用户姓名",classify = CreateType.DEFAULT,notNull = true)
	protected String createUserName = "系统";
	
	@Attribute(alias = "创建用户id",comment = "记录创建用户的唯一标识",classify = CreateType.DEFAULT,notNull = true)
	protected Long createUserId = 0L;
	
	@Attribute(alias = "最后更新用户姓名",comment = "记录最后更新的用户姓名",classify = CreateType.DEFAULT,notNull = true)
	protected String updateUserName = "系统";
	
	@Attribute(alias = "最后更新用户id",comment = "记录最后更新的唯一标识",classify = CreateType.DEFAULT,notNull = true)
	protected Long updateUserId = 0L;
	
	protected Integer v = 1;
	
	@Attribute(alias = "记录是否可见",comment = "记录是否可见，1表可见，0表不可见，用在逻辑删除",classify = CreateType.DEFAULT,notNull = true)
	protected Integer visiable = 1;
	
	public void clearInitData() {
		this.createTime = null;
		this.updateTime = null;
		this.createUserName = null;
		this.createUserId = null;
		this.updateUserName = null;
		this.updateUserId = null;
		this.v = null;
		this.visiable = null;
	}
	



}
