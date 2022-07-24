/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin;

import java.util.Map;

/**
 * <p>Title: PluginServiceI </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
public interface PluginServiceI {

	public void start(String applicationRootPath,Map<String, Object> paras);
	
	public void stop(String applicationRootPath);
 
}
