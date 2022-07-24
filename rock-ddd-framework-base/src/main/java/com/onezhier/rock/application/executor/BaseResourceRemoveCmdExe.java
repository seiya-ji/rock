/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.application.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.onezhier.rock.application.domain.BaseFactory;
import com.onezhier.rock.client.Response;
import com.onezhier.rock.client.req.Command;
import com.onezhier.rock.framework.application.executor.CommandCommonExecutorI;
import com.onezhier.rock.framework.domain.EntityObject;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>Title: BaseResourceRemoveCmdExe </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class BaseResourceRemoveCmdExe implements CommandCommonExecutorI<Command, Response>  {

	@Autowired
    private BaseFactory baseFactory;

	
   
	@Transactional
	@Override
	public <T extends EntityObject> Response execute(Command cmd, Class<T> clz) {
		BaseResourceRemoveCmd removeCmd = (BaseResourceRemoveCmd) cmd;
		T entity = this.baseFactory.reBuild(removeCmd.getId(), clz);
		this.baseFactory.remove(entity);
        return Response.buildSuccess();
	}
	
	@Data
	@AllArgsConstructor
	public static class BaseResourceRemoveCmd extends  Command{
		
		private Long id;
		
	}
}
