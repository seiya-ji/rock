/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.domain.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import com.onezhier.rock.client.extension.BizScenario;


/**
 * <p>Title: Extension </p>
 * <p>Description: 扩展点标识</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Extension {
    String bizId()  default BizScenario.DEFAULT_BIZ_ID;
    String useCase() default BizScenario.DEFAULT_USE_CASE;
    String scenario() default BizScenario.DEFAULT_SCENARIO;
}
