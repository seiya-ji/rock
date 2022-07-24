/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.application.executor.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onezhier.rock.application.BaseApplicationConvertor;
import com.onezhier.rock.application.domain.BaseFactory;
import com.onezhier.rock.client.DTO;
import com.onezhier.rock.client.req.Query;
import com.onezhier.rock.client.resp.SingleResponse;
import com.onezhier.rock.common.util.CommonBeanUtils;
import com.onezhier.rock.framework.application.executor.QueryCommonExecutorI;
import com.onezhier.rock.framework.application.service.ServiceContextHolder;
import com.onezhier.rock.framework.domain.EntityObject;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>Title: BaseResourceGetQueryExe </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class BaseResourceGetQueryExe
		implements QueryCommonExecutorI<BaseResourceGetQueryExe.BaseResourceGetQueryCmd, SingleResponse<DTO>> {

	
	@Autowired
	private BaseFactory baseFactory;

	

	@Override
	public <T extends DTO> SingleResponse execute(BaseResourceGetQueryCmd query, Class<T> clz) {
		EntityObject entity = this.baseFactory.reBuild(query.getId(), ServiceContextHolder.get().getEntityClass());

		return SingleResponse.of(CommonBeanUtils.copy(entity, clz));
	}
	
	@Data
	@AllArgsConstructor
	public static class BaseResourceGetQueryCmd extends Query {

		private Long id;

	}
}
