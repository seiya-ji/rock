/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.onezhier.rock.framework.component.ComponentServiceI;


/**
 * <p>Title: ExtensionRepository </p>
 * <p>Description: 扩展点存储器</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component("rock-component-repository")
public class ComponentRepository {


    @SuppressWarnings("rawtypes")
	private Map<String, ComponentServiceI> componentServiceRepo = new HashMap<>();

    @SuppressWarnings("rawtypes")
	public Map<String, ComponentServiceI> getComponentServiceRepo() {
        return componentServiceRepo;
    }

}
