/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.onezhier.rock.exception.SysException;
import com.pz.rock.core.loader.SpringBootClassLoader;
import com.pz.rock.core.plugin.PluginServiceI;

/**
 * <p>Title: PluginServiceImpl </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
public class PluginServiceImpl implements PluginServiceI{

	public static String JAR_PATH = "JAR_PATH_KEY";
	
	@Override
	public void start(String applicationRootPath,Map<String, Object> paras) {
		String jarFilePath = applicationRootPath+File.separator+"runtime"+File.separator+paras.get(PluginServiceImpl.JAR_PATH);
		try {
			SpringBootClassLoader.createClassLoader(new SpringBootClassLoader.Launcher(jarFilePath),getClass().getClassLoader() );
		} catch (IOException e) {
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public void stop(String applicationRootPath) {
		// TODO Auto-generated method stub
		
	}

	
 
}
