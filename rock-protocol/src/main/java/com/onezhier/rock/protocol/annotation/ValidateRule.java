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
public @interface ValidateRule {
	
	String name() default "";
	
	/**
	 * 错误提示语
	 * @return
	 */
	String prompt() default "";
}
