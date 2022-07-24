#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */
package ${package};

import lombok.extern.slf4j.Slf4j;
//import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.pz.rock.core.context.CustomizeWebApplicationContext;
import com.pz.rock.core.loader.CustomizeJarLauncher;


/**
 * @author jihb
 * @date 2022年06月01日 11:33
 */
@ComponentScan(basePackages = { "${package}","com.pz.rock.core","com.onezhier.rock"})
@EnableJpaRepositories(basePackages= {"${package}"})
@EntityScan(basePackages= {"${package}"})
//@EnableDubbo(scanBasePackages = {"${package}"})
@EnableAutoConfiguration
@Slf4j
public class StartApplication {

    public static void main(String[] args) {
    	System.setProperty("spring.application.name", "${parentArtifactId}");
        new SpringApplicationBuilder(StartApplication.class)
        .resourceLoader(CustomizeJarLauncher.queryApplication("${parentArtifactId}")==null?new DefaultResourceLoader(): new DefaultResourceLoader(CustomizeJarLauncher.queryApplication("${parentArtifactId}").getClassLoader()))
    	.contextClass(CustomizeWebApplicationContext.class).profiles("${parentArtifactId}")
        .web(WebApplicationType.SERVLET)
        .run(args);

        log.info("${parentArtifactId} 服务已启动.");

    }

}
