package com.pz.rock.core.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.loader.JarLauncher;
import org.springframework.boot.loader.archive.JarFileArchive;

import com.onezhier.rock.client.req.PageQuery;
import com.onezhier.rock.client.resp.PageResponse;
import com.onezhier.rock.framework.application.AbstractLauncheApplication;
import com.onezhier.rock.framework.dal.db.DO;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

public class CustomizeJarLauncher extends JarLauncher {
	
	public static ClassLoader currentClassLoader;
	
	private Long currentApplicationId;
	
	private String currentApplicationName;
	                        
	public static Map<Long, AbstractLauncheApplication> launcheApplicationMap = new HashMap<Long, AbstractLauncheApplication>();
	
	public CustomizeJarLauncher(Long applicationId ,String applicationName ,File file) throws IOException {
		super(new JarFileArchive(file));
		this.currentApplicationId = applicationId;
		this.currentApplicationName = applicationName;
	}
	
	@Override
	protected ClassLoader createClassLoader(URL[] urls) throws Exception {
//		System.err.println(" CustomizeJarLauncher createClassLoader  class classloader: "+getClass().getClassLoader()+" thread classloader: "+Thread.currentThread().getContextClassLoader()+" parent:"+getClass().getClassLoader().getParent());
		CustomizeLaunchedURLClassLoader classLoader =   new CustomizeLaunchedURLClassLoader(isExploded(), getArchive(), urls, getClass().getClassLoader());
		CustomizeJarLauncher.launcheApplicationMap.put(this.currentApplicationId, new LauncheApplication(this.currentApplicationId, this.currentApplicationName, classLoader));
		CustomizeJarLauncher.currentClassLoader = classLoader;
		return classLoader;
	}
	
	@Override
	public void launch(String[] args) throws Exception {
//		System.err.println(" CustomizeJarLauncher class classloader: "+getClass().getClassLoader()+" thread classloader: "+Thread.currentThread().getContextClassLoader());
		super.launch(args);
	}
	

	
	@Slf4j
	public static class LauncheApplication extends AbstractLauncheApplication{

		public LauncheApplication(@NonNull Long applicationId, @NonNull String applicationName,
				@NonNull URLClassLoader classLoader) {
			super(applicationId, applicationName, classLoader);
		}

		@Override
		public Object invoke(Class<?> modelClass, String methodName,Map<String, Class<?>> paramsClassMap,Long id,List<Object> argsList) {
			InvokeHandler handler = this.getApplicationContext().getBean(InvokeHandler.class);
			if(handler==null) {
				log.warn("未找到invoke处理类，请先实现！");
				return null;
			}
			return handler.invoke(this, modelClass,  methodName, paramsClassMap, id, argsList);
		}

		@Override
		public PageResponse<DO> query(String modelName, PageQuery pageQuery) {
			InvokeHandler handler = this.getApplicationContext().getBean(InvokeHandler.class);
			if(handler==null) {
				log.warn("未找到invoke处理类，请先实现！");
				return null;
			}
			return handler.query(this, modelName, pageQuery);
		}
		
//	
//		
//		@NonNull
//		private CustomizeLaunchedURLClassLoader classLoader;
		
		
		
		
		
	}

}
