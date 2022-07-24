package com.pz.rock.core;
///**
// * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
// */
//package com.fzd.rock.core;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.server.ConfigurableWebServerFactory;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
////import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//
//import com.fzd.rock.core.context.CustomizeApplicationContext;
//import com.fzd.rock.core.osgi.CustomizedFrameworkLauncher;
//import com.fzd.rock.core.osgi.CustomizedServletConfig;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * <p>Title: StartApplication </p>
// * <p>Description: TODO </p>
// *
// * @author stephen.ji
// * @version v0.1
// * @date 2021/8/15 3:51 下午
// */
//
//@SpringBootApplication(scanBasePackages= {"com.fzd.rock.core"} )
//@EnableConfigurationProperties(CustomizedServletConfig.class)
//@ComponentScan(basePackages = { "cn.leansight.idoos.mom","cn.leansight.idoos.momrefactor", "com.fzd.rock" })
////@EnableJpaRepositories(basePackages= {"cn.leansight.idoos.mom","cn.leansight.idoos.momrefactor"})
//@EntityScan(basePackages= {"cn.leansight.idoos.mom","cn.leansight.idoos.momrefactor"})
//@Slf4j
//public class StartApplication implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>{
//
//	public static  SpringApplicationBuilder parentBuilder;
//	
////	@Autowired
////	private  OSGIAutoConfiguration springAutoConfiguration;
//	
//	
//	
//	public static void main(String[] args) {
//		SpringApplicationBuilder parentBuilder  = new SpringApplicationBuilder(StartApplication.class);
//		parentBuilder.contextClass(CustomizeApplicationContext.class);
//		
//		
//		SpringApplication sa = parentBuilder.build();
//		
//		ConfigurableApplicationContext applicationContext = sa.run(args);
//		
//		//SpringApplication.run(StartApplication.class, args);
////		MyBridge bridge = new MyBridge(StartApplication.springAutoConfiguration.getServletConfig());
////		try {
////			bridge.init();
////		} catch (ServletException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		System.out.println("init classloader"+ StartApplication.class.getClassLoader() +" resourceloader: "+sa.getResourceLoader()+" applicationContext:"+applicationContext);
//		
//	}
//
//	@Override
//	public void customize(ConfigurableWebServerFactory factory) {
//		factory.setPort(9900);
//	
//	}
//	
//
//
//   
//
//}
