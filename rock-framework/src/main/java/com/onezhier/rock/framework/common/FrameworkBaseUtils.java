package com.onezhier.rock.framework.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.aop.support.AopUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;
import org.springframework.util.StringUtils;

import com.onezhier.rock.exception.ExceptionFactory;
import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.protocol.annotation.Attribute;
import com.onezhier.rock.protocol.annotation.Model;
import com.onezhier.rock.protocol.annotation.UniqueConstraint;

import groovy.lang.Tuple2;

public class FrameworkBaseUtils {

	public static Object invoke(Object obj, String method,Class<?>[] paramClasses, Object... args) {
		Method m = ReflectionUtils.findMethod(obj.getClass(), method,paramClasses==null?new Class<?>[0]:paramClasses);
		if (m == null) {
			throw ExceptionFactory.sysException("为找到相应的方法");
		}
		try {
			Object result = m.invoke(obj, args);
			return result;
		} catch (ReflectiveOperationException e) {
			throw ExceptionFactory.sysException(e.getMessage(), e);
		}

	}

	public static Class<? extends DO> getDOClazz(Model entity) {
		String className = entity.storeClassName();
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
	
	
	public static Model getEntityAnno (DO do_) {
		Class<DO>  entityObjClz = (Class<DO>) AopUtils.getTargetClass(do_);
		Model entityAnno = entityObjClz.getAnnotation(Model.class);
		return entityAnno;
	}
	
	public static DO constractInstance (DO do_,String[] attrNames) {
		Class<DO>  entityObjClz = (Class<DO>) AopUtils.getTargetClass(do_);
		
		try {
			
			DO result = entityObjClz.newInstance();
			
			for(String attr : attrNames) {
				String conditionVal = null;
				if(attr.contains(":")) {
					attr = attr.split(":")[0];
					conditionVal = attr.split(":")[1];
				}
				Field field = ReflectionUtils.findField(entityObjClz, attr);
				ReflectionUtils.makeAccessible(field);
				Object value =  ReflectionUtils.getField(field, do_);
				//不满足条件值的 返回无值对象
				if(conditionVal!=null&&!value.toString().equals(conditionVal)) {
					return entityObjClz.newInstance();
				}
				ReflectionUtils.setField(field, result, value);
			}
			
			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SysException(e.getMessage(),e);
		}
		
	}
	
	public static DO constractInstance (Class<DO> entityObjClz,Map<String, Object> attrMap) {
		
		try {
			
			DO result = entityObjClz.newInstance();
			
			for(Entry<String, Object> entry : attrMap.entrySet()) {
				Field field = ReflectionUtils.findField(entityObjClz, entry.getKey());
				ReflectionUtils.makeAccessible(field);
				Object value =  entry.getValue();
				if( entry.getValue().getClass() != field.getType()) {
					 value =  ReflectionUtils.accessibleConstructor(field.getType(),String.class ).newInstance(entry.getValue());
				}
				
				ReflectionUtils.setField(field, result, value);
			}
			
			return result;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			throw new SysException(e.getMessage(),e);
		}
		
	}
	
	public static Attribute getAttributeAnno (DO do_,String attrName) {
		Class<DO>  entityObjClz = (Class<DO>) AopUtils.getTargetClass(do_);
		Field field =  ReflectionUtils.findField(entityObjClz, attrName);
		if(field==null) {
			return null;
		}
		return field.getAnnotation(Attribute.class);
	}
	
	public static List<Attribute> getAttributeAnnos (DO do_) {
		List<Attribute>  result = new LinkedList<Attribute>();
		Class<DO>  entityObjClz = (Class<DO>) AopUtils.getTargetClass(do_);
		 ReflectionUtils.doWithFields(entityObjClz, new FieldCallback() {
			
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				result.add(field.getAnnotation(Attribute.class));
			}
		}, new FieldFilter() {
			
			@Override
			public boolean matches(Field field) {
				return field.getAnnotation(Attribute.class)!=null;
			}
		});
		 return result;
	}
	
	
	public static Object getValByField(DO do_,String attrName) {
		Field field = ReflectionUtils.findField(do_.getClass(), attrName);
		if (field == null) {
			throw ExceptionFactory.sysException("为找到相应的属性");
		}
		field.setAccessible(true);
		return ReflectionUtils.getField(field, do_);
	}
	
	public static Object getValByMethod(DO do_,String attrName) {
		Method method = ReflectionUtils.findMethod(do_.getClass(), "get"+org.apache.commons.lang.StringUtils.capitalise(attrName));
		if (method == null) {
			throw ExceptionFactory.sysException("为找到相应的属性");
		}
		return ReflectionUtils.invokeMethod(method, do_);
	}
	
	public static void setAttr(DO do_,String attrName,Object value) {
		Field field = ReflectionUtils.findField(do_.getClass(), attrName);
		if (field == null) {
			throw ExceptionFactory.sysException("为找到相应的属性");
		}
		field.setAccessible(true);
		ReflectionUtils.setField(field, do_, value);
		
	}
	
	public static List<Tuple2<String[],String>> getUniqueConstraints (DO do_) {
		List<Tuple2<String[],String>> result = new LinkedList<>();
		Model e = FrameworkBaseUtils.getEntityAnno(do_);
		if(e!=null&&e.uniqueConstraints().length>0) {
			for(UniqueConstraint  uniqueKey :e.uniqueConstraints()) {
				if(uniqueKey.attributes().length>0) {
					result.add(new Tuple2(uniqueKey.attributes(), uniqueKey.prompt()) );
				}
				
			}
		}
		
		Class<DO>  entityObjClz = (Class<DO>) AopUtils.getTargetClass(do_);
		 ReflectionUtils.doWithFields(entityObjClz, new FieldCallback() {
			
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				result.add(new Tuple2(new String[] {field.getName()}, field.getName()+"不可重复！")  );
			}
		}, new FieldFilter() {
			
			@Override
			public boolean matches(Field field) {
				Attribute attr = field.getAnnotation(Attribute.class);
				return attr!=null&&attr.unique();
			}
		});
		
		return result;
	}
	
	
	
	
}
