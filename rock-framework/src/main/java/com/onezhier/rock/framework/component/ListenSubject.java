/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Title: ListenSubject </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ListenSubject {
	
	//发起的对象
	public abstract String source() default "";
	
	//发起的行为 
	public abstract String operate() default "";
	
	
	//public abstract Class<ComponentServiceI> relayService() ;

}
