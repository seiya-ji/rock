package com.onezhier.rock.application;

import org.springframework.context.annotation.ComponentScan;

/**
 * <p>Title: BaseApplicationBootstrap </p>
 * <p>Description: 引擎启动入口类 </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@ComponentScan(basePackages= {"com.pz.rock.application"} )
//@EnableConfigurationProperties(CustomizedServletConfig.class)
public class BaseApplicationBootstrap {
    
}