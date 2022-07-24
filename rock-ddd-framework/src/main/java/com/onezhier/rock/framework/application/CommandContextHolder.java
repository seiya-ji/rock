/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application;

import com.onezhier.rock.client.req.Command;


/**
 * <p>Title: CommandContextHolder </p>
 * <p>Description: 命令上下文持有者 </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
public class CommandContextHolder {
    // 构造方法私有化
    private CommandContextHolder(){
    	
    };
 
    private static final ThreadLocal<Command> context = new ThreadLocal<>();
 
    public static void set(Command command){
        context.set(command);
    }
 
   
    public static Command get(){
        return context.get();
    }
 
    /**
     * 清除当前线程内引用，防止内存泄漏
     */
    public static void remove(){
        context.remove();
    }
}