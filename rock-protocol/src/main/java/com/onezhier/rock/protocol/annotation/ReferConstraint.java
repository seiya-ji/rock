package com.onezhier.rock.protocol.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 引用约束
 * @author 
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface ReferConstraint {
	
	/**
	 * 对应的自己的属性，默认为主键 id
	 * @return
	 */
	String[] selfAttrNames() default {"id"};
	
	/**
	 * 引用对方的模型类
	 * @return
	 */
	Class<?> otherClass() default Class.class;
	

	/**
	 * 对方的属性名字
	 * @return
	 */
	String[] otherAttrNames() default {"id"};
	
	/**
	 * 错误提示语
	 * @return
	 */
	String prompt() default "";
}
