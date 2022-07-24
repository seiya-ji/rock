/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.onezhier.rock.client.DTO;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.domain.EntityObject;
/**
 * <p>Title: AbstractConvertor </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
public abstract class AbstractConvertor<E extends EntityObject, D extends DTO, P extends DO>
		implements ApplicationConvertorI<E, D, P> {

	@Override
	public List<E> convertList(List<D> dtoList) {
		if (CollectionUtils.isEmpty(dtoList)) {
			return new LinkedList<>();
		}
		return dtoList.stream().map(t -> {
			return this.convert(t);
		}).collect(Collectors.toList());
	}

}
