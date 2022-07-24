#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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

import com.fzd.rock.core.context.CustomizeWebApplicationContext;
import com.fzd.rock.core.loader.CustomizeJarLauncher;



@ComponentScan(basePackages = { "${package}"})
@EnableJpaRepositories(basePackages= {"${package}"})
@EntityScan(basePackages= {"${package}"})
//@EnableDubbo(scanBasePackages = {"${package}"})
@EnableAutoConfiguration
@Slf4j
public class StartApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(StartApplication.class)
        .resourceLoader(CustomizeJarLauncher.currentClassLoader==null?new DefaultResourceLoader(): new DefaultResourceLoader(CustomizeJarLauncher.currentClassLoader))
    	.contextClass(CustomizeWebApplicationContext.class).profiles("${parentArtifactId}")
        .web(WebApplicationType.SERVLET)
        .run(args);

        log.info("${parentArtifactId} 服务已启动.");

    }

}
