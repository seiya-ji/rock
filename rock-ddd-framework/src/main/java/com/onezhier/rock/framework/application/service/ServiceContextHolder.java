/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application.service;

/**
 * <p>Title: ServiceContextHolder </p>
 * <p>Description: 服务上下文持有者 </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
public class ServiceContextHolder {
	
	
    // 构造方法私有化
    private ServiceContextHolder(){
    	
    }
 
    private static final ThreadLocal<ServiceContext> context = new ThreadLocal<>();
 
    public static void set(ServiceContext userContext){
        context.set(userContext);
    }
 
   
    public static ServiceContext get(){
        return context.get();
    }
 
    /**
     * 清除当前线程内引用，防止内存泄漏
     */
    public static void remove(){
        context.remove();
    }
}