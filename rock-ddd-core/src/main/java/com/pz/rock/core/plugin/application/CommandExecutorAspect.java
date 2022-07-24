/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.application;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.onezhier.rock.client.Request;
import com.onezhier.rock.client.req.Command;
import com.onezhier.rock.framework.application.CommandContextHolder;

/**
 * <p>Title: CommandExecutorAspect </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
@Aspect
@Component
public class CommandExecutorAspect {

	@Pointcut("execution(* *.CommonExecutorI+.execute(..))")
	public void executePoint() {

	}

	@Before("executePoint()") // 前置通知
	public void doExecuteBefore(JoinPoint jp) {
		Request command = (Request) jp.getArgs()[0];
		CommandContextHolder.set((Command) command);
	}

	@After("executePoint()") // 后置通知
	public void doExecuteAfter() {

		CommandContextHolder.remove();
	}

}
