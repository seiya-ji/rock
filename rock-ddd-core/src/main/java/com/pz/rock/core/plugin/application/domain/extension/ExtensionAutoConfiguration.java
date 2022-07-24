/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.application.domain.extension;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pz.rock.core.plugin.application.domain.extension.registe.ExtensionBootstrap;
import com.pz.rock.core.plugin.application.domain.extension.registe.ExtensionRegister;



/**
 * <p>Title: ExtensionAutoConfiguration </p>
 * <p>Description: 扩展点自动配置类</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Configuration
public class ExtensionAutoConfiguration {

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean(ExtensionBootstrap.class)
    public ExtensionBootstrap bootstrap() {
        return new ExtensionBootstrap();
    }

    @Bean
    @ConditionalOnMissingBean(ExtensionRepository.class)
    public ExtensionRepository repository() {
        return new ExtensionRepository();
    }

    @Bean
    @ConditionalOnMissingBean(ExtensionExecutor.class)
    public ExtensionExecutor executor() {
        return new ExtensionExecutor();
    }

    @Bean
    @ConditionalOnMissingBean(ExtensionRegister.class)
    public ExtensionRegister register() {
        return new ExtensionRegister();
    }

}
