/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;

/**
 * <p>Title: CustomizeBeanUtils </p>
 * <p>Description: </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
public class CommonBeanUtils extends BeanUtils{
    /**
     * 构造函数
     */
    public CommonBeanUtils() {
    }

    /**
     * Transform java bean to map
     *
     * @param bean bean
     * @return map
     */
    public static Map<String, Object> bean2Map(Object bean) {
    	
        Map<String, Object> properties = Maps.newHashMap();
        if (bean == null) {
            return properties;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : propertyDescriptors) {
                String beanName = pd.getName();
                if (!StringUtils.equals(beanName, "class")) {
                    Method getter = pd.getReadMethod();
                    Object beanValue = getter.invoke(bean);
                    properties.put(beanName, beanValue);
                }
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    /**
     * Transform map to java bean
     *
     * @param properties map
     * @return bean
     */
    public static <T> T map2Bean(Map<String, Object> properties, Class<T> clazz) {
        if (properties == null) {
            return null;
        }
        try {
            T bean = clazz.newInstance();
            CommonBeanUtils.populate(bean, properties);
            return bean;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            T bean = clazz.newInstance();
            CommonBeanUtils.copyProperties(bean, source);
            return bean;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
    
    
	
	
    
    
    

}
