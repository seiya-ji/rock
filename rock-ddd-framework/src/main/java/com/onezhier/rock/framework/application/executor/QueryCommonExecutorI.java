/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application.executor;


import com.onezhier.rock.client.DTO;
import com.onezhier.rock.client.Request;
import com.onezhier.rock.client.Response;
import com.onezhier.rock.client.req.Query;
import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.domain.EntityObject;


/**
 * <p>Title: QueryCommonExecutorI </p>
 * <p>Description: 通用的查询执行器能力接口</p>
 *
 * @author jihb
 * @version v0.1
 * @date 2021/03/12
 */
public interface QueryCommonExecutorI<C extends Query, R extends Response>  extends CommonExecutorI<C, R> , QueryExecutorI{

    /**
     * 执行
     *
     * @param cmd 指令
     * @return 执行结果
     */
    public default R execute(C cmd ) {
    	throw new SysException("no support ...");
    }
    
    public  <T extends DTO> R execute(C query , Class<T> clz);
}
