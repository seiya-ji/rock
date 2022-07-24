/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.application.domain.event;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pz.rock.core.plugin.application.domain.event.registe.EventBootstrap;



/**
 * <p>Title: EventAutoConfiguration </p>
 * <p>Description: 事件自动化配置类</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Configuration
public class EventAutoConfiguration {

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean(EventBootstrap.class)
    public EventBootstrap bootstrap() {
        return new EventBootstrap();
    }


}
