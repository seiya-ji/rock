package com.pz.rock.core.loader;

import java.lang.reflect.Field;
import java.net.URL;

import org.springframework.boot.loader.LaunchedURLClassLoader;
import org.springframework.boot.loader.archive.Archive;
import org.springframework.boot.loader.archive.JarFileArchive;
import org.springframework.util.ReflectionUtils;

import com.onezhier.rock.exception.SysException;

public class CustomizeLaunchedURLClassLoader extends LaunchedURLClassLoader {

	public CustomizeLaunchedURLClassLoader(boolean exploded, Archive rootArchive, URL[] urls, ClassLoader parent) {
		super(exploded, rootArchive, urls, parent);
	}

//	@Override
//	public Class<?> loadClass(String name) throws ClassNotFoundException {
//		return this.findClass(name);
//	}
	
	public static void closeJarFileArchive(LaunchedURLClassLoader classLoader ) {
		 try {		
	          Field archiveField =   ReflectionUtils.findField(LaunchedURLClassLoader.class, "rootArchive");
			  archiveField.setAccessible(true);  
		      JarFileArchive  archiveObj = (JarFileArchive)archiveField.get(classLoader);  
		      archiveObj.close();
         }catch (Exception e) {
			throw new SysException(e.getMessage(),e);
		}
	}

	
}
