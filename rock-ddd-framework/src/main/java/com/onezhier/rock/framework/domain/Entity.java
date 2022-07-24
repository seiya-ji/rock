///**
// * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
// */
//package com.onezhier.rock.framework.domain;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Inherited;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//
//
///**
// * <p>Title: Entity </p>
// * <p>Description: Entity, Entity Object is prototype and is not thread-safe </p>
// *
// * @author stephen.ji
// * @version v0.1
// * @date 2021/4/1 3:51 下午
// */
//@Inherited
//@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.TYPE})
//@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//public @interface Entity {
//
//	/**
//	 * 执行持久化操作的class全路径名称
//	 * @return
//	 */
//	 String storeClassName()  default "";
//
//	/**
//	 * 模型别名
//	 * @return
//	 */
//	String alias() default "";
//
//	/**
//	 * 模型说明
//	 * @return
//	 */
//	 String description() default "";
//
//	/**
//	 * 模型分组
//	 * @return
//	 */
//	String group() default "";
//	
//}
