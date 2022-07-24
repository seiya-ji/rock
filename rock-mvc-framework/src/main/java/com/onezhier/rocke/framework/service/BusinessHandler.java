package com.onezhier.rocke.framework.service;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.onezhier.rock.framework.dal.db.DO;

/**
 * 业务处理器，支持各业务前后自定义业务
 * @author jihb
 *
 * @param <D>
 */
public interface BusinessHandler<D extends DO> {
	
	public default void  postCreate(D newDO,ApplicationContext applicationContext) {
		return ;
	}

	public default void  postCreateBatch(List<D> newDOList,ApplicationContext applicationContext) {
		return ;
	}
	
	public default void  postModify(D oldDo,D newDo,ApplicationContext applicationContext) {
		return ;
	}
	
	public default void  postRemove(D oldDO,ApplicationContext applicationContext) {
		return ;
	}
	
	
	public default void  postRemoveBatch(List<D> oldDOList,ApplicationContext applicationContext) {
		return ;
	}
	
	public default void  postGet(D oldDo,ApplicationContext applicationContext) {
		return ;
	}
	
	public default void  preCreateBatch(List<D> newDOList,ApplicationContext applicationContext) {
		return ;
	}
	
}
