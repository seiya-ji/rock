/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.domain;


/**
 * <p>Title: RespositoryI </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
public interface RespositoryI {
	
	public  EntityObject get(Object id,Class<? extends EntityObject> clz) ;
	
	public EntityObject save (EntityObject entity) ;
	

}
