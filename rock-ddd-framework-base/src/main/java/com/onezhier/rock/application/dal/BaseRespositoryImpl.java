/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.application.dal;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onezhier.rock.application.common.FrameworkUtils;
import com.onezhier.rock.application.dal.db.BaseDao;
import com.onezhier.rock.exception.InfrastructureException;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.domain.EntityObject;
import com.onezhier.rock.framework.domain.RespositoryI;
import com.onezhier.rock.protocol.annotation.Model;

/**
 * <p>Title: BaseRespositoryImpl </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class BaseRespositoryImpl implements RespositoryI {

	@Autowired
	private BaseDao baseDao;

	@Autowired
	private BaseInfrastructureConvertor convertor;

	public EntityObject get(Object id, Class<? extends EntityObject> entityObjClz) {

		Model entity = entityObjClz.getAnnotation(Model.class);
		
		DO do_ = this.baseDao.findById(FrameworkUtils.getDOClazz(entity), id);
		if(do_==null) {
			throw new InfrastructureException("dal中未找到相关数据");
		}
		return this.convertor.convert(do_,entityObjClz);

	}
	
	

	@SuppressWarnings("unchecked")
	public EntityObject save(EntityObject entity) {
		Class<EntityObject>  entityObjClz = (Class<EntityObject>) AopUtils.getTargetClass(entity);
		Model entityAnno = entityObjClz.getAnnotation(Model.class);
		DO do_ = this.convertor.convert(entity,FrameworkUtils.getDOClazz(entityAnno));
		if(do_==null) {
			throw new InfrastructureException("dal中未找到相关数据");
		}
		DO newDO = this.baseDao.persist(do_);
		return this.convertor.convert(newDO, entity.getClass());
		
	}

	@SuppressWarnings("unchecked")
	public void remove(EntityObject entity) {
		Class<EntityObject>  entityObjClz = (Class<EntityObject>) AopUtils.getTargetClass(entity);
		Model entityAnno = entityObjClz.getAnnotation(Model.class);
		DO do_ =  this.convertor.convert(entity,FrameworkUtils.getDOClazz(entityAnno));
		if(do_==null) {
			throw new InfrastructureException("dal中未找到相关数据");
		}
		this.baseDao.remove(do_);
	}

}
