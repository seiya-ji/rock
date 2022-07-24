package com.pz.rock.core.osgi;
//package com.fzd.rock.core.osgi;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebInitParam;
//import javax.servlet.annotation.WebServlet;
//
//import org.eclipse.equinox.servletbridge.BridgeServlet;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//@WebServlet(urlPatterns = "/**",initParams= {@WebInitParam(name="frameworkLauncherClass",value="com.fzd.rock.core.osgi.CustomizedFrameworkLauncher")})
//public class MyBridge extends BridgeServlet{
//	
//	@Autowired
//	private CustomizedServletConfig servletConfig;
//	
////	@PostConstruct
////	public void preInit() {
////		try {
////			this.init(this.servletConfig);
////		} catch (ServletException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
//	
////	public MyBridge(ServletConfig servletConfig) {
////		super();
////		try {
////			this.init(servletConfig);
////		} catch (ServletException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
//
//	
//}
