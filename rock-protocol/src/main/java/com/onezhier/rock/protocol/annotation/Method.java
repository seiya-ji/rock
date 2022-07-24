package com.onezhier.rock.protocol.annotation;


import java.lang.annotation.*;


@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Method {

    /**
     * 是否暴露
     * @return
     */
    boolean exposed() default false;

    /**
     * 方法名称
     * @return
     */
    String alias() default "";

    /**
     * 方法说明
     * @return
     */
    String comment() default "";
    
    /**
     * 创建类型，默认内置，自定义
     * @return
     */
    CreateType classify() default CreateType.CUSTOMIZED ;

}
