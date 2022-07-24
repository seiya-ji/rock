/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.application.executor.query;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.onezhier.rock.application.BaseApplicationConvertor;
import com.onezhier.rock.application.common.FrameworkUtils;
import com.onezhier.rock.application.dal.db.BaseDao;
import com.onezhier.rock.client.DTO;
import com.onezhier.rock.client.req.PageQuery;
import com.onezhier.rock.client.resp.PageResponse;
import com.onezhier.rock.exception.ExceptionFactory;
import com.onezhier.rock.framework.application.executor.QueryCommonExecutorI;
import com.onezhier.rock.framework.application.service.ServiceContextHolder;
import com.onezhier.rock.framework.dal.db.DO;

/**
 * <p>Title: BaserResourceQueryExe </p>
 * <p>Description: </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class BaserResourceQueryExe implements QueryCommonExecutorI<PageQuery, PageResponse<?>> {

	@Autowired
	private BaseDao baseDao;

	@Autowired
	private BaseApplicationConvertor baseApplicationConvertor;

	@Override
	public <T extends DTO> PageResponse<T> execute(PageQuery query, Class<T> clz) {
		Pageable pageable = PageRequest.of(query.getPageIndex() - 1, query.getPageSize());
		try {
			
			DO example = (ServiceContextHolder.get().getDoClass()==DO.class?FrameworkUtils.getDOClazz(ServiceContextHolder.get().getEntityClass()):ServiceContextHolder.get().getDoClass()).newInstance();
			BeanUtils.copyProperties(query, example);

			Page<DO> doPage = this.baseDao.findAll(example, pageable);
			List<T> dtoList = doPage.getContent().stream().map((t) -> {
				return this.baseApplicationConvertor.convert(t, clz);
			}).collect(Collectors.toList());

			return PageResponse.of(dtoList, Integer.valueOf(String.valueOf(doPage.getTotalElements())),
					query.getPageSize(), query.getPageIndex());
		} catch (InstantiationException | IllegalAccessException e) {
			throw ExceptionFactory.sysException("实例化失败", e);
		}
	}
	
	
	

}
