package com.onezhier.rock.protocol.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 关联关系，表对象直接的关系，如组合，聚合
 * @author 86181
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface AssociationRelation {

	
	/**
	 * 对方实体的类型
	 * @return
	 */
	Class<?> otherClass() default Class.class;
	

	/**
	 * 对方的属性名字
	 * @return
	 */
	String otherAttrName() default "";
	
	/**
	 * 对应的自己的属性，默认为主键 id
	 * @return
	 */
	String selfAttrName() default "id";
	
	/**
	 * 关联的对方的数量
	 * @return
	 */
	Amount otherAmount() default Amount.SIGNAL;
	
	/**
	 * 关联类型，组合，聚合
	 * @return
	 */
	Association associationType() default Association.AGGREGATION;
	
	
	
	
	public enum Amount{
		SIGNAL,MANY;
	}
	
	public enum Association{
		COMPOSE,AGGREGATION;
	}
}
