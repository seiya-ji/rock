package com.onezhier.rocke.framework.service;

import com.onezhier.rock.framework.dal.db.DO;

/**
 * 模型对象值设置器，支持自定义设置值
 * @author jihb
 *
 * @param <D>
 */
public interface DOSettor<D extends DO> {
	
	/**
	 * 保存前设置值
	 * @param do_
	 * @return
	 */
	public default D  createSet(D do_) {
		return do_;
	}

	/**
	 * 修改前设置值
	 * @param oldDo
	 * @param newDo
	 * @return
	 */
	public default D  modifySet(D oldDo,D newDo) {
		return  newDo;
	}
	
	
}
