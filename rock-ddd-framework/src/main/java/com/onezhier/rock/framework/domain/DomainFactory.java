/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.domain;

import org.springframework.beans.factory.annotation.Autowired;

import com.onezhier.rock.framework.application.ApplicationContextI;



/**
 * <p>Title: DomainFactory </p>
 * <p>Description: 领域对象工厂类 </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */

public class DomainFactory {
   
	@Autowired
	protected ApplicationContextI applicationContext;
	
    public  <T> T create(Class<T> entityClz){
        return this.applicationContext.getBean(entityClz);
    }

	

}
