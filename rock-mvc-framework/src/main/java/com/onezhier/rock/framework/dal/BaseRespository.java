/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.onezhier.rock.framework.dal.db.DO;



/**
 * <p>Title: BaseRespository </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
 public  interface  BaseRespository<T extends DO , I > extends JpaRepository<T,I> ,JpaSpecificationExecutor<T> {
	
}
