package com.onezhier.rock.protocol.annotation;


import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EnumTag {

    /**
     * 别名
     * @return
     */
    String value() default "";
}
