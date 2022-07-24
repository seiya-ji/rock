package com.onezhier.rock.framework.application;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

import org.springframework.context.ApplicationContext;

import com.onezhier.rock.client.req.PageQuery;
import com.onezhier.rock.client.resp.PageResponse;
import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.dal.db.DO;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract class AbstractLauncheApplication {
	

	
	@NonNull
	private Long applicationId;
	
	@NonNull
	private String applicationName;
	
	@NonNull
	private URLClassLoader classLoader;
	
	private ApplicationContext applicationContext;
	
	
	public abstract Object invoke(Class<?> modelClass, String methodName,Map<String, Class<?>> paramsClassMap,Long id,List<Object> argsList);
	
	public abstract PageResponse<DO> query(String modelName,PageQuery pageQuery);

	public interface InvokeHandler {
		
		public  Object invoke(AbstractLauncheApplication launcheApplication,Class modelClass, String methodName,Map<String, Class<?>> paramsClassMap,Long id,List<Object> argsList);
		
		public   PageResponse<DO> query(AbstractLauncheApplication launcheApplication,String modelName,PageQuery pageQuery);
	}
	
	public void destroy() {
		
		try {
			this.classLoader.close();
		} catch (IOException e) {
			throw new SysException(e.getMessage(),e);
		}
		
		 
		 sun.misc.ClassLoaderUtil.releaseLoader(this.classLoader);

		this.classLoader = null;
		this.applicationContext = null;
	}
	
	
	
	public void releaseJar() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		 // 查找URLClassLoader中的ucp  
        Object ucpObj = null;  
        Field ucpField = URLClassLoader.class.getDeclaredField("ucp");  
        ucpField.setAccessible(true);  
        ucpObj = ucpField.get(this.classLoader);  
        URL[] list = this.classLoader.getURLs();  
        for(int i=0;i<list.length;i++){  
            // 获得ucp内部的jarLoader  
            Method m = ucpObj.getClass().getDeclaredMethod("getLoader", int.class);  
            m.setAccessible(true);  
            Object jarLoader = m.invoke(ucpObj, i);  
            String clsName = jarLoader.getClass().getName();  
            if(clsName.indexOf("JarLoader")!=-1){  
                m = jarLoader.getClass().getDeclaredMethod("ensureOpen");  
                m.setAccessible(true);  
                m.invoke(jarLoader);  
                m = jarLoader.getClass().getDeclaredMethod("getJarFile");  
                m.setAccessible(true);  
                JarFile jf = (JarFile)m.invoke(jarLoader);  
                // 释放jarLoader中的jar文件  
                jf.close();  
                System.out.println("release jar: "+jf.getName());  
            }  
        }  
	}

}
