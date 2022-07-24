package com.onezhier.rocke.framework.service;

import com.onezhier.rock.framework.dal.db.DO;

/**
 * 业务验证，自定义各个功能的业务验证逻辑
 * @author jihb
 *
 */
public interface BusinessValidater {
	
	/***
	 *  业务验证，创建前调用
	 * @param do_
	 */
	public default void  createValidate(DO do_) {
		return ;
	}

	/**
	 * 业务验证，编辑业务前调用
	 * @param oldDo
	 * @param newDo
	 */
	public default void  modifyValidate(DO oldDo,DO newDo) {
		return ;
	}
	
	/**
	 * 业务验证，移除业务前调用
	 * @param do_
	 */
	public default void  removeValidate(DO do_) {
		return ;
	}
}
