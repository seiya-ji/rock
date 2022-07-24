/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.application.dal.db;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.StringUtils;

import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.dal.db.DO;

import groovy.lang.Tuple2;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * <p>Title: BaseDao </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/4/1 3:51 下午
 */
@Component
public class BaseDao {
	
	@Autowired
    @PersistenceContext
    private EntityManager entityManager;
	
	public <T extends DO> T findById(Class<T> clz, Object id) {
		
		return this.entityManager.find(clz, id);
		
	}
	
	public <T extends DO> T persist (T do_) {
		T persistDo = do_;
		Method idMethod = ReflectionUtils.findMethod(do_.getClass(), "getId");
		if(idMethod==null) {
			throw new SysException("do中没有找到获取主键的方法");
		}
		Object id = ReflectionUtils.invokeMethod(idMethod, do_);
		if(id!=null) {
			 persistDo = (T) this.entityManager.find(do_.getClass(), ReflectionUtils.invokeMethod(idMethod, do_));
			BeanUtils.copyProperties(do_, persistDo);
		}
		
		this.entityManager.persist(persistDo);
		this.entityManager.flush();
		return persistDo;
	}
	
	public <T extends DO> void remove(T do_) {
		T persistDo = do_;
		Method idMethod = ReflectionUtils.findMethod(do_.getClass(), "getId");
		if(idMethod==null) {
			throw new SysException("do中没有找到获取主键的方法");
		}
		Object id = ReflectionUtils.invokeMethod(idMethod, do_);
		if(id!=null) {
			 persistDo = (T) this.entityManager.find(do_.getClass(), ReflectionUtils.invokeMethod(idMethod, do_));
			 this.entityManager.remove(persistDo);
		}
		
	}

	
	
	public <T extends DO> Page<T>  findAll(T example, Pageable pageable,Tuple2<Operator, String>... specialOperator){
		
		List<Tuple2<Operator, String>> specialList = specialOperator!=null?Arrays.asList(specialOperator):new LinkedList<>();
		CriteriaBuilder criteriaBuilder =  this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query  = (CriteriaQuery<T>) criteriaBuilder.createQuery(example.getClass());
		Root<T> root = (Root<T>) query.from(example.getClass());
		List<Predicate> predicateList  = new LinkedList<Predicate>();
		ReflectionUtils.doWithFields(example.getClass(), new FieldCallback() {
			
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				String methodName = "get"+StringUtils.capitalize(field.getName());
				Method mothod = ReflectionUtils.findMethod(example.getClass(), methodName);
				if(mothod==null) {
					throw  new SysException("没有找到相应的方法！方法名："+methodName);
				}
				Object val = ReflectionUtils.invokeMethod(mothod, example);
				if(Objects.isNull(val)) {
					return ;
				}
//				for(Tuple2<Operator, String> tuple : specialList) {
//					if(field.getName().equalsIgnoreCase(tuple.getV2())) {
//						if(tuple.getV1()==Operator.LIKE) {
//							predicateList.add(criteriaBuilder.like(root.get(field.getName()), ReflectionUtils.invokeMethod(mothod, example)));
//						}
//					}
//				}
				predicateList.add(criteriaBuilder.equal(root.get(field.getName()), ReflectionUtils.invokeMethod(mothod, example)));
			}
		});
		if(!predicateList.isEmpty()) {
			query.where(predicateList.toArray(new Predicate[predicateList.size()]));
		}

		if(pageable.getSort().isSorted()){
			query.orderBy(toOrders(pageable.getSort(), root, criteriaBuilder));
		}

		TypedQuery<T> typeQuery = this.entityManager.createQuery(query);
		typeQuery.setFirstResult(Long.valueOf(pageable.getOffset()).intValue());
		typeQuery.setMaxResults(pageable.getPageSize());
		List<T> content =  typeQuery.getResultList();
		
		CriteriaBuilder totalCb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> totalQuery  = totalCb.createQuery(Long.class);
		if(!predicateList.isEmpty()) {
			totalQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
		}
		totalQuery.multiselect(totalCb.count(totalQuery.from(example.getClass())));
		Long total = this.entityManager.createQuery(totalQuery).getSingleResult();
		
		return new PageImpl<>(content, pageable, total.longValue());
		
	}
	
	public enum Operator  {
		LIKE;
	}
	
}
