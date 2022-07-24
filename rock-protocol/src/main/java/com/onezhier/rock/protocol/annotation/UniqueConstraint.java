package com.onezhier.rock.protocol.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 唯一约束
 * @author 86181
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface UniqueConstraint {
	
	String name() default "";
	
	/**
	 * 唯一属性，可以多个属性联合唯一，属性支持条件值判定 
	 * 如：xxx:1 表示 当xxx等于1的时候启用联合唯一
	 * @return
	 */
	String[] attributes() default {};
	
	/**
	 * 错误提示语
	 * @return
	 */
	String prompt() default "";
}
