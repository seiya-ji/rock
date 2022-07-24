/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.dal;

import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.domain.EntityObject;

/**
 * <p>Title: InfrastructureConvertorI </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version 1.0.0
 * @date Sep 17, 2021
 */
public interface InfrastructureConvertorI<E extends EntityObject,P extends DO> {

	public P convert(E entity);
	
	public E convert(P do_);
	
	
	
}
