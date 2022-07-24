/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.onezhier.rock.client.DTO;
import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.domain.EntityObject;

/**
 * <p>Title: BaseApplicationConvertor </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class BaseApplicationConvertor  {
	
	@SuppressWarnings("unchecked")
	public <E extends EntityObject, D extends DTO> D convert(E entity,Class<D> clz) {
		D result = null;
		try {
			 result = (D) ReflectionUtils.accessibleConstructor(clz).newInstance();
			 BeanUtils.copyProperties(entity, result);
		} catch (ReflectiveOperationException e) {
			throw new SysException(e.getMessage());
		} 
		return result;
	}
	
	public  <E extends EntityObject, D extends DTO> E convert(D dto,Class<E> clz) {
		E result = null;
		try {
			 result = (E) ReflectionUtils.accessibleConstructor(clz).newInstance();
			 BeanUtils.copyProperties(dto, result);
		} catch (ReflectiveOperationException e) {
			throw new SysException(e.getMessage());
		} 
		return result;
	}
	
	public <D extends DTO, P extends DO> D convert(P do_,Class<D> clz) {
		D result = null;
		try {
			 result = (D) ReflectionUtils.accessibleConstructor(clz).newInstance();
			 BeanUtils.copyProperties(do_, result);
		} catch (ReflectiveOperationException e) {
			throw new SysException(e.getMessage());
		} 
		return result;
	}
	
	public <E extends EntityObject, D extends DTO> List<E> convertList(List<D> dtoList,Class<E> clz){
		Assert.isNull(dtoList,"dto集合不能为空");
		return dtoList.stream().map((t)->{return this.convert(t, clz);}).collect(Collectors.toList());
	}

}
