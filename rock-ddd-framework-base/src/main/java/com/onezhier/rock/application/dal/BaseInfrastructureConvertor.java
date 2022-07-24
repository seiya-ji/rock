/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.application.dal;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.domain.EntityObject;

/**
 * <p>Title: BaseInfrastructureConvertor </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class BaseInfrastructureConvertor {
	
	public <E extends EntityObject, P extends DO> P convert(E entity,Class<P> clz,String... ignorAttr) {
		P result = null;
		try {
			 result = (P) ReflectionUtils.accessibleConstructor(clz).newInstance();
			 BeanUtils.copyProperties(entity, result,ignorAttr);
		} catch (ReflectiveOperationException e) {
			throw new SysException(e.getMessage(),e);
		} 
		return result;
	}
	
	public  <E extends EntityObject,  P extends DO> E convert(P do_,Class<E> clz) {
		E result = null;
		try {
			 result = (E) ReflectionUtils.accessibleConstructor(clz).newInstance();
			 BeanUtils.copyProperties(do_, result);
		} catch (ReflectiveOperationException e) {
			throw new SysException(e.getMessage());
		} 
		return result;
	}
	

}
