/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.application;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onezhier.rock.client.DTO;
import com.onezhier.rock.framework.application.UserContext;
import com.onezhier.rock.framework.application.UserContextHolder;
import com.onezhier.rock.framework.application.UserInfoService;
import com.onezhier.rock.framework.application.service.ApplicationService;
import com.onezhier.rock.framework.application.service.ServiceContext;
import com.onezhier.rock.framework.application.service.ServiceContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Title: ApplicationServiceAspect </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
@Aspect
@Component
@Slf4j
public class ApplicationServiceAspect {
	
	@Autowired(required=false)
    private UserInfoService userInfoService;
	
    //@Pointcut("@within(com.onezhier.rock.framework.application.service.ApplicationService)  ")
    @Pointcut("execution(*  com..BaseController+.*(..))  ")
	public void servicePoint(){

    }
    @Before("servicePoint() ")  //前置通知
    public void doServiceBefore(JoinPoint jp){
    	log.info(" appliction service aop start .."+jp.toString());
    	if(UserContextHolder.get()==null) {
    		UserContext uc = new UserContext();
        	if(this.userInfoService!=null) {
        		uc.setUserInfo(this.userInfoService.getUser(jp.getArgs()));
        	}
        	UserContextHolder.set(uc);
    	}
    	
    	
    	
    	
    	ServiceContext sc = new ServiceContext();
    	ApplicationService asnno = (ApplicationService) jp.getTarget().getClass().getAnnotation(ApplicationService.class);
    	sc.setDoClass(asnno.doClass());
    	sc.setDtoClass(asnno.dtoClass());
    	sc.setListDtoClass(asnno.listDtoClass());
    	if(jp.getArgs()!=null) {
    		for(Object arg :jp.getArgs()) {
    			if(arg instanceof DTO) {
    				sc.setCurrentDTO((DTO)arg);
    			}
    			
    		}
    	}
    	
    	ServiceContextHolder.set(sc);
    	
    }
    @After("servicePoint()") //后置通知
    public void doServiceAfter(){
    	
    	UserContextHolder.remove();
    	
    	ServiceContextHolder.remove();
    }
	
}
