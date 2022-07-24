/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application;

/**
 * <p>Title: AbstractApplicationContextProxy </p>
 * <p>Description: 应用上下文代理类 </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */

public abstract class AbstractApplicationContextProxy  {
	
    public abstract  <T> T getBean(Class<T> targetClz);
	 
	public abstract Object getBean(String claz) ;

    public abstract <T> T getBean(String name, Class<T> requiredType) ;

    public abstract <T> T getBean(Class<T> requiredType, Object... params) ;
    
   
}
