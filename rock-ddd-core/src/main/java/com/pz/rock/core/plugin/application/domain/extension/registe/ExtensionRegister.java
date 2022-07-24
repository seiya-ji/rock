/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.pz.rock.core.plugin.application.domain.extension.registe;

import javax.annotation.Resource;

import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import com.onezhier.rock.client.extension.BizScenario;
import com.onezhier.rock.framework.domain.extension.Extension;
import com.onezhier.rock.framework.domain.extension.ExtensionCoordinate;
import com.onezhier.rock.framework.domain.extension.ExtensionPointI;
import com.pz.rock.core.plugin.application.domain.extension.ExtensionRepository;


/**
 * <p>Title: ExtensionRegister </p>
 * <p>Description: 扩展点注册器</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class ExtensionRegister{

    @Resource
    private ExtensionRepository extensionRepository;

    public final static String EXTENSION_EXTPT_NAMING = "ExtPt";


    public void doRegistration(ExtensionPointI extensionObject){
        Class<?>  extensionClz = AopUtils.getTargetClass(extensionObject);
        Extension extensionAnn = extensionClz.getDeclaredAnnotation(Extension.class);
        BizScenario bizScenario = BizScenario.valueOf(extensionAnn.bizId(), extensionAnn.useCase(), extensionAnn.scenario());
        ExtensionCoordinate extensionCoordinate = new ExtensionCoordinate(calculateExtensionPoint(extensionClz), bizScenario.getUniqueIdentity());
        ExtensionPointI preVal = extensionRepository.getExtensionRepo().put(extensionCoordinate, extensionObject);
        if (preVal != null) {
            throw new RuntimeException("Duplicate registration is not allowed for :" + extensionCoordinate);
        }

    }

    /**
     * @param targetClz
     * @return
     */
    private String calculateExtensionPoint(Class<?> targetClz) {
        Class[] interfaces = targetClz.getInterfaces();
        if (interfaces == null || interfaces.length == 0)
            throw new RuntimeException("Please assign a extension point interface for "+targetClz);
        for (Class intf : interfaces) {
            String extensionPoint = intf.getSimpleName();
            if (extensionPoint.contains(EXTENSION_EXTPT_NAMING))
                return intf.getName();
        }
        throw new RuntimeException("Your name of ExtensionPoint for "+targetClz+" is not valid, must be end of "+ EXTENSION_EXTPT_NAMING);
    }

}