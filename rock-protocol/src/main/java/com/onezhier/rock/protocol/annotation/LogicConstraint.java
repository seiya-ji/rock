package com.onezhier.rock.protocol.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 逻辑约束-对结构上关联的对象，自定义约束要求。
 * 使用spring el表达式，计算出true false，应用关联的属性上。
 * 支持本对象关联处理
 * @author 
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface LogicConstraint {
	
	/**
	 * 对应的自己的属性，默认为主键 id
	 * 可以为空
	 * @return
	 */
	String selfAttrName() ;
	
	/**
	 * 引用对方的模型类
	 * 可以为空
	 * @return
	 */
	Class<?> otherClass() ;
	
	/**
	 * 对方的属性名字
	 * 可以为空
	 * @return
	 */
	String otherAttrName() ;
	

	
	
	/**
	 * el表达式，返回必须是true或false的
	 * @return
	 */
	String expression() default "";
	
	/**
	 * 错误提示语
	 * @return
	 */
	String prompt() default "";
	
	

}
