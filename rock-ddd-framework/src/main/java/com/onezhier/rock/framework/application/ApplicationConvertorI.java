/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application;

import java.util.List;

import com.onezhier.rock.client.DTO;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.domain.EntityObject;

/**
 * <p>Title: ApplicationConvertorI </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
public interface ApplicationConvertorI<E extends EntityObject,D extends DTO,P extends DO> {

	public D convert(E entity);
	
	public E convert(D dto);
	
	public D convert(P do_);
	
	public List<E> convertList(List<D> dtoList);
	
}
