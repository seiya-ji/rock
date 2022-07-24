package com.pz.rock.core.osgi.configuration;
///**
// * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
// */
//package com.fzd.rock.core.osgi.configuration;
//
//import java.util.Map;
//
//import javax.servlet.Servlet;
//
//import org.eclipse.equinox.servletbridge.BridgeServlet;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.fzd.rock.core.osgi.CustomizedServletConfig;
//
///**
// * <p>Title: OSGIAutoConfiguration </p>
// * <p>Description: TODO </p>
// *
// * @author stephen.ji
// * @version v0.1
// * @date 2021/8/15 3:51 下午
// */
//@Configuration
//public class OSGIAutoConfiguration {
//   
//	@Autowired
//	private CustomizedServletConfig customizedServletConfig;
//	
//	public  CustomizedServletConfig getServletConfig() {
//		return this.customizedServletConfig;
//	}
//	
//	
//	@Bean
//	public ServletRegistrationBean<Servlet> bridgeServlet() {
//		ServletRegistrationBean<Servlet> result = new ServletRegistrationBean<Servlet>(new BridgeServlet(), "/**");
//		for(Map.Entry<String, String> entry :this.customizedServletConfig.getConfig().entrySet()) {
//			result.addInitParameter(entry.getKey(), entry.getValue());
//		}
//		result.setEnabled(true);
//		result.setLoadOnStartup(1);
//		return result;
//	}
//	
////	public ServletListenerRegistrationBean<EventListener> servletListener(){
////		
////	}
//	
//	
//}
