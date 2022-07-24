/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.onezhier.rock.client.Response;
import com.onezhier.rock.framework.dal.db.DO;

/**
 * <p>
 * Title: ApplicationService
 * </p>
 * <p>
 * Description: ApplicationService, ApplicationService Object is prototype and
 * is not thread-safe
 * </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
//@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface ApplicationService {

	//Class<? extends EntityObject> entityClass() default EntityObject.class ;

	Class<? extends DO> doClass() default DO.class ;
	

	Class<? extends Response> listDtoClass() default Response.class ;

	Class<? extends Response> dtoClass() default Response.class;
}
