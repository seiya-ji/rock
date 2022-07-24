package com.onezhier.rock.protocol.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface Attribute {

    /**
     * 属性别名，用于展示
     * @return
     */
    String alias() default "";

    /**
     * 备注信息
     * @return
     */
    String comment() default "";
    
    
    // 逻辑约束...
    
    /**
     * 是否唯一
     * @return
     */
    boolean unique() default false;
    
    /**
     * 不可为空
     * @return
     */
    boolean notNull() default false;
    
    
    /**
     * 创建类型，默认内置，自定义
     * @return
     */
    CreateType classify() default CreateType.CUSTOMIZED ;
    
    
    ValidateRule[] validateRules() default {};
    
    
//    /**
//     * 可为空
//     * @return
//     */
//    NotNull notNull();
//  
//    // 数字
//    
//    /**
//     * 验证表达式
//     * @return
//     */
//    String validate() default "";
}
