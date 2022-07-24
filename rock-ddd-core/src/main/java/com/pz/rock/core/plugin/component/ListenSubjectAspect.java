/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onezhier.rock.framework.application.UserContext;
import com.onezhier.rock.framework.application.UserContextHolder;
import com.onezhier.rock.framework.application.UserInfoService;

/**
 * <p>Title: ListenSubjectAspect </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
@Component
public class ListenSubjectAspect {
	
	@Autowired(required=false)
    private UserInfoService userInfoService;
	
    @Pointcut("@annotation(com.pz.rock.component.ListenSubject)")  //@annotation声明以注解的方式来定义切点
    public void point(){

    }
    @Before("point()")  //前置通知
    public void doServiceBefore(JoinPoint jp){
    	if(this.userInfoService==null) {
    		return;
    	}
    	UserContext uc = new UserContext();
    	uc.setUserInfo(this.userInfoService.getUser(jp.getArgs()));
    	UserContextHolder.set(uc);
    }
    @After("point()") //后置通知
    public void doServiceAfter(){
    	
    	UserContextHolder.remove();
    }
	
}
