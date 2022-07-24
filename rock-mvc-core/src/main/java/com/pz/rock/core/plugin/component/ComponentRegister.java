/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.component;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.onezhier.rock.framework.component.ComponentService;
import com.onezhier.rock.framework.component.ComponentServiceI;

/**
 * <p>Title: ComponentRegister </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
@Component
public class ComponentRegister {

	@Autowired
	@Qualifier(value = "rock-component-repository")
	private  ComponentRepository componentRepository;
	
	 public void doRegistration(ComponentServiceI<?, ?> componentService){
	        Class<?>  cmpClz = AopUtils.getTargetClass(componentService);
	        ComponentService componentServiceAnno = cmpClz.getDeclaredAnnotation(ComponentService.class);
	        ComponentServiceI<?, ?> preVal = componentRepository.getComponentServiceRepo().put(componentServiceAnno.value(), componentService);
	        if (preVal != null) {
	            throw new RuntimeException("Duplicate registration is not allowed for :" + componentServiceAnno.value());
	        }

	    }
	
	
}
