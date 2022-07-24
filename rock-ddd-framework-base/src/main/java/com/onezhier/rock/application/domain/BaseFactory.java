/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.application.domain;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onezhier.rock.application.dal.BaseRespositoryImpl;
import com.onezhier.rock.framework.domain.DomainFactory;
import com.onezhier.rock.framework.domain.EntityObject;


/**
 * <p>Title: BaseFactory </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class BaseFactory extends DomainFactory{
	
	@Autowired
	private BaseRespositoryImpl baseRespository;
	
	 public  <T> T create(Class<T> entityClz,Object... bean){
		
	        T result =  this.applicationContext.getBean(entityClz);
	        BeanUtils.copyProperties(bean, result);
	        return result;
	 }
	 
	 @SuppressWarnings("unchecked")
	public  <T extends EntityObject> T reBuild(Long id,Class<T> entityClz){
		
		return  (T) this.baseRespository.get(id,entityClz);
	 }
	 
	 public <T extends EntityObject> void remove(T entity) {
		 this.baseRespository.remove(entity);
	 }

	 public <T extends EntityObject> T save(T entity) {
		 return (T) this.baseRespository.save(entity);
	 }
	 
}
