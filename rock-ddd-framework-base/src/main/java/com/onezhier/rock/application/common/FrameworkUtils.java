package com.onezhier.rock.application.common;

import org.springframework.aop.support.AopUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.common.FrameworkBaseUtils;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.domain.EntityObject;
import com.onezhier.rock.protocol.annotation.Model;

public class FrameworkUtils extends FrameworkBaseUtils{

//	public static Object invoke(Object obj, String method,Class<?>[] paramClasses, Object... args) {
//		Method m = ReflectionUtils.findMethod(obj.getClass(), method,paramClasses==null?new Class<?>[0]:paramClasses);
//		if (m == null) {
//			throw ExceptionFactory.sysException("为找到相应的方法");
//		}
//		try {
//			Object result = m.invoke(obj, args);
//			return result;
//		} catch (ReflectiveOperationException e) {
//			throw ExceptionFactory.sysException(e.getMessage(), e);
//		}
//
//	}

//	public static Class<? extends DO> getDOClazz(Entity entity) {
//		String className = entity.storeClassName();
//		if(StringUtils.isEmpty(className)) {
//			throw new SysException("配置不正确，领域实体对应do类型没有配置");
//		}
//		Class<?> clazz;
//		try {
//			clazz = ClassUtils.forName(className,ClassUtils.getDefaultClassLoader());
//		} catch (ClassNotFoundException e) {
//			throw new SysException(e.getMessage(),e);
//		}
//		if (!DO.class.isAssignableFrom(clazz)) {
//			throw new SysException("配置不正确，领域实体对应的不是do");
//		}
//		@SuppressWarnings("unchecked")
//		Class<? extends DO> clz = (Class<? extends DO>) clazz;
//		return clz;
//	}
//	
	
	public static Class<? extends DO> getDOClazz (EntityObject entityObj ) {
		Class<EntityObject>  entityObjClz = (Class<EntityObject>) AopUtils.getTargetClass(entityObj);
		Model entityAnno = entityObjClz.getAnnotation(Model.class);
		String className = entityAnno.storeClassName();
		if(StringUtils.isEmpty(className)) {
			throw new SysException("配置不正确，领域实体对应do类型没有配置");
		}
		Class<?> clazz;
		try {
			clazz = ClassUtils.forName(className,ClassUtils.getDefaultClassLoader());
		} catch (ClassNotFoundException e) {
			throw new SysException(e.getMessage(),e);
		}
		if (!DO.class.isAssignableFrom(clazz)) {
			throw new SysException("配置不正确，领域实体对应的不是do");
		}
		@SuppressWarnings("unchecked")
		Class<? extends DO> clz = (Class<? extends DO>) clazz;
		return clz;
	}
	
	public static Class<? extends DO> getDOClazz (Class<? extends EntityObject> entityObjClz ) {
		Model entityAnno = entityObjClz.getAnnotation(Model.class);
		String className = entityAnno.storeClassName();
		if(StringUtils.isEmpty(className)) {
			throw new SysException("配置不正确，领域实体对应do类型没有配置");
		}
		Class<?> clazz;
		try {
			clazz = ClassUtils.forName(className,ClassUtils.getDefaultClassLoader());
		} catch (ClassNotFoundException e) {
			throw new SysException(e.getMessage(),e);
		}
		if (!DO.class.isAssignableFrom(clazz)) {
			throw new SysException("配置不正确，领域实体对应的不是do");
		}
		@SuppressWarnings("unchecked")
		Class<? extends DO> clz = (Class<? extends DO>) clazz;
		return clz;
	}
	
}
