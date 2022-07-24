/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application.executor;


import com.onezhier.rock.client.Request;
import com.onezhier.rock.client.Response;


/**
 * <p>Title: CommonExecutorI </p>
 * <p>Description: 命令执行器的接口定义</p>
 *
 * @author jihb
 * @version v0.1
 * @date 2021/03/12
 */
public interface CommonExecutorI<C extends Request, R extends Response> extends CommandExecutorI , QueryExecutorI {

    /**
     * 执行
     *
     * @param cmd 指令
     * @return 执行结果
     */
    public  R execute(C cmd );
   

}
