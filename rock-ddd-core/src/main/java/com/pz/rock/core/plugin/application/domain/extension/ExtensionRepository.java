/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.application.domain.extension;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.onezhier.rock.framework.domain.extension.ExtensionCoordinate;
import com.onezhier.rock.framework.domain.extension.ExtensionPointI;


/**
 * <p>Title: ExtensionRepository </p>
 * <p>Description: 扩展点存储器</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class ExtensionRepository {

    public Map<ExtensionCoordinate, ExtensionPointI> getExtensionRepo() {
        return extensionRepo;
    }

    private Map<ExtensionCoordinate, ExtensionPointI> extensionRepo = new HashMap<>();


}
