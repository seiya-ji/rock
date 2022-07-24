/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application;

import java.util.Set;

import com.onezhier.rock.framework.application.UserContext.UserInfo;

/**
 * <p>Title: CommandContextHolder </p>
 * <p>Description: 命令上下文持有者 </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
public class UserContextHolder {
	
	
    // 构造方法私有化
    private UserContextHolder(){
    	
    }
 
    private static final ThreadLocal<UserContext> context = new InheritableThreadLocal<>();
 
    public static UserContext initContext(String userId, String name) {
    	UserContext context = new UserContext();
    	UserContext.UserInfo user = new UserContext.UserInfo();
    	user.setId(userId);
    	user.setName(name);
    	context.setUserInfo(user);
    	UserContextHolder.set(context);
    	return context;
    }
    
    public static void set(UserContext userContext){
        context.set(userContext);
    }
 
   
    public static UserContext get(){
        return context.get();
    }
 
    /**
     * 清除当前线程内引用，防止内存泄漏
     */
    public static void remove(){
        context.remove();
    }
}