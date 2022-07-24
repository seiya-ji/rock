/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application.executor;


import com.onezhier.rock.client.Request;
import com.onezhier.rock.client.Response;
import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.domain.EntityObject;


/**
 * <p>Title: BaseCommonExecutorI </p>
 * <p>Description: 命令执行器的接口定义</p>
 *
 * @author jihb
 * @version v0.1
 * @date 2021/03/12
 */
public interface CommandCommonExecutorI<C extends Request, R extends Response>  extends CommonExecutorI<C, R>{

    /**
     * 执行
     *
     * @param cmd 指令
     * @return 执行结果
     */
    public default R execute(C cmd ) {
    	throw new SysException("no support ...");
    }
    
    public  <T extends EntityObject> R execute(C cmd , Class<T> clz);
}
