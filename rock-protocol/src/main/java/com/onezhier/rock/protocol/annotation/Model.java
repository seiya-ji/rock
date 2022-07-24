/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.protocol.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



/**
 * <p>Title: Entity </p>
 * <p>Description: Entity, Entity Object is prototype and is not thread-safe </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface Model {

	/**
	 * 执行持久化操作的class全路径名称
	 * @return
	 */
	 String storeClassName()  default "";

	/**
	 * 模型别名
	 * @return
	 */
	String alias() default "";

	/**
	 * 模型说明
	 * @return
	 */
	 String description() default "";

	/**
	 * 模型分组
	 * @return
	 */
	String group() default "";
	
	/**
	 * 自动审计 即自动设置操作人员
	 * @return
	 */
	boolean autoAudit() default true;
	
	
	/**
	 * 是否逻辑删除
	 * @return
	 */
	boolean logic() default false;
	
	/**
	 * 唯一约束
	 * @return
	 */
	UniqueConstraint[] uniqueConstraints() default {};
	
	/**
	 * 引用约束
	 * @return
	 */
	ReferConstraint[] referConstraints() default {};
	
	
	
	/**
	 * 与其他实体的关联关系
	 * @return
	 */
	AssociationRelation[] associationRelationes() default {};
	
	
	/**
	 * 逻辑约束，支持关联对象处理
	 * @return
	 */
	LogicConstraint[] logicConstraints() default {};
	
	
	
	
	
	
}
