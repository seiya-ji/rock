/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.convertor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.NumberUtils;
import org.mapstruct.Named;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;

import com.onezhier.rock.client.DTO;
import com.onezhier.rock.client.Response;
import com.onezhier.rock.client.req.Query;
import com.onezhier.rock.client.req.Query.MatcherCfg;
import com.onezhier.rock.client.resp.PageResponse;
import com.onezhier.rock.framework.dal.db.DO;

import groovy.lang.Tuple2;
import groovy.lang.Tuple3;

/**
 * <p>Title: ApplicationConvertorI </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
public interface ApplicationConvertorI<D extends DTO,P extends DO,Q extends Query,LRESPD extends Response,IRESPD extends Response>  {

	@Named("dtoConvertPo")
	public P convert(D dto);
	
	@Named("poConvertDto")
	public D convert(P do_);
	
	@Named("queryConvertPo")
	public P convert(Q q);
	
	@Named("dtoConvertPoList")
	public List<P> convertDOList(List<D> dtoList);
	
	@Named("poConvertDtoList")
	default List<D> convertList(List<P> doList){
		if (CollectionUtils.isEmpty(doList)) {
			return new LinkedList<>();
		}
		return doList.stream().map(t -> {
			return this.convert(t);
		}).collect(Collectors.toList());
	}
	
	default Tuple2<P,Specification<P>> convertQuerySpecification(Q query){
		 P do_ = ApplicationConvertorI.this.convert(query);
	      do_.clearInitData();
	      List<Tuple3<String, Object, String>>  attrList = new LinkedList<>();
	      Map<String, Tuple3<String, Object, String>> attrMap = new HashMap<String, Tuple3<String,Object,String>>();
	      //设置equal
	      ReflectionUtils.doWithFields(do_.getClass(), new FieldCallback() {
			
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				attrMap.put(field.getName(), new Tuple3<String, Object, String>(field.getName(), ReflectionUtils.getField(field, do_), "equal"));
			}
		},new FieldFilter() {
			
			@Override
			public boolean matches(Field field) {
				field.setAccessible(true);
				Object val =   ReflectionUtils.getField(field, do_);
				return val!=null;
			}
		});
	      //设置like
	      List<MatcherCfg> matcherList =  query.getMatcheres();
	      matcherList.stream().forEach((t)->{
	    	  Tuple3<String, Object, String> attr =   attrMap.get(t.getName());
	    	  if(attr!=null) {
	    		  attr.set(2, "like:"+t.getType());
		    	  attrList.add(attr);
		    	  attrMap.remove(t.getName());
	    	  }
	    	
	      });
	      //设置 null
	      query.getNullConfigs().stream().forEach((t)->{
	    	  attrList.add(new Tuple3<String, Object, String>(t.getName(), new Object(),"null:"+(t.isNot()?"isNot":"is")));
	      });
	      
	      attrList.addAll(attrMap.values());
	      Specification spec =  new Specification<P>() {

			@Override
			public Predicate toPredicate(Root<P> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicateList = new LinkedList<>();
				for(Tuple3<String, Object, String> attr : attrList) {
					if(attr.getV3().equals("equal")) {
						predicateList.add(builder.equal(root.get(attr.getV1()),attr.getV2())) ;
					}else if(attr.getV3().startsWith("like")) {
						if(attr.getV3().equals("like:A")) {
							predicateList.add(builder.like(root.get(attr.getV1()),attr.getV2()+"%")) ;
						}else if(attr.getV3().equals("like:B")) {
							predicateList.add(builder.like(root.get(attr.getV1()),"%"+attr.getV2()+"%")) ;
						}else {
							predicateList.add(builder.like(root.get(attr.getV1()),"%"+attr.getV2())) ;
						}
					
					}else if(attr.getV3().startsWith("null")) {
						if(attr.getV3().equals("null:is")) {
							predicateList.add(builder.isNull(root.get(attr.getV1()))) ;
						}else {
							predicateList.add(builder.isNotNull(root.get(attr.getV1()))) ;
						}
						
					}
					
				}
				
				 return query.where(predicateList.toArray(new Predicate[predicateList.size()] )).getRestriction();
		
				
			}
		};
		
		return new Tuple2<P, Specification<P>>(do_, spec);
	}
	
	default Tuple2<P, ExampleMatcher> convertQueryCondition(Q query){
		
	      ExampleMatcher matcher =  ExampleMatcher.matchingAll();
	      List<MatcherCfg> matcherList =  query.getMatcheres();
	      for (MatcherCfg matcherReq:matcherList){
	             if(matcherReq.getType().equals("A")){
	                 matcher=matcher.withMatcher(matcherReq.getName(), ExampleMatcher.GenericPropertyMatcher::startsWith);
	             }else if(matcherReq.getType().equals("B")){
	                 matcher=matcher.withMatcher(matcherReq.getName(), ExampleMatcher.GenericPropertyMatcher::contains);
	             }else if(matcherReq.getType().equals("E")){
	                 matcher=matcher.withMatcher(matcherReq.getName(), ExampleMatcher.GenericPropertyMatcher::endsWith);
	             }else {
	                 matcher=matcher.withMatcher(matcherReq.getName(), ExampleMatcher.GenericPropertyMatcher::exact);
	             }

	     
	     }
	      P do_ = this.convert(query);
	      do_.clearInitData();
	     return Tuple2.tuple(do_, matcher);
		
	}
	
	@Named("poConvertDetailRespDTO")
	public IRESPD convertDetailResp(P do_);
	
	@Named("poConvertListRespDTO")
	public LRESPD convertListResp(P do_);
	
	@Named("poConvertListRespDTOList")
	default List<LRESPD> convertRespList(List<P> doList){
		if (CollectionUtils.isEmpty(doList)) {
			return new LinkedList<>();
		}
		return doList.stream().map(t -> {
			return this.convertListResp(t);
		}).collect(Collectors.toList());
	}
	
	default  Page<LRESPD> convertRespPage(Page<P> doPage){

		return doPage.map(new Function<P, LRESPD>() {

			@Override
			public LRESPD apply(P t) {
				
				return ApplicationConvertorI.this.convertListResp(t);
			}
		});
	}
	
	default PageResponse<LRESPD> convertRespPageResponse(Page<P> doPage){
		Page<LRESPD> page = this.convertRespPage(doPage);
		return PageResponse.of(page.getContent(), NumberUtils.stringToInt(page.getTotalElements()+""), page.getPageable().getPageSize(),  NumberUtils.stringToInt(page.getPageable().getOffset()+"") );
	}
	
	
}
