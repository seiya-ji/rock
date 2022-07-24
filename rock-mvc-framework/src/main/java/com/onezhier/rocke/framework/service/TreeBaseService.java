package com.onezhier.rocke.framework.service;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.aop.framework.AopContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;

import com.onezhier.rock.client.req.Query;
import com.onezhier.rock.framework.common.FrameworkBaseUtils;
import com.onezhier.rock.framework.convertor.ApplicationConvertorI;
import com.onezhier.rock.framework.dal.BaseRespository;
import com.onezhier.rock.framework.dal.db.TreeDO;
import com.onezhier.rock.protocol.annotation.CreateType;
import com.onezhier.rock.protocol.annotation.Model;
import com.onezhier.rock.protocol.annotation.Method;

import groovy.lang.Tuple2;


/**
 *  
 * @author jihb
 *
 * @param <D>
 */
public class TreeBaseService<D extends TreeDO> extends BaseService<D>{

	public TreeBaseService(BaseRespository<? extends TreeDO, ? extends Serializable> respository,
			ApplicationConvertorI convertor) {
		super(respository, convertor);
	}
	
	@Method(alias = "查找树结构",comment = "查找树结构，不分页",exposed = true, classify  = CreateType.DEFAULT)
	@Transactional(readOnly = true)
	public List<D> queryTree(Query query){
		
		Tuple2<D, ExampleMatcher>  tuple = this.convertor.convertQueryCondition(query);
		Model  entity =  FrameworkBaseUtils.getEntityAnno(tuple.getFirst());
		if(entity!=null&&entity.logic()) {
			FrameworkBaseUtils.setAttr(tuple.getV1(), "visiable", 1);
		}
		List<D> list = this.respository.findAll(Example.of(tuple.getV1(),tuple.getV2()));
		return list.stream().filter(e-> e.getPid()==null).peek(d -> d.setChildrens(getChildrens(d, list))).collect(Collectors.toList());
	}
	
	

	 public  List<TreeDO> getChildrens(D root, List<D> all){
	        return all.stream().filter(d->{return d.getPid()!=null&&d.getPid().equals(root.getId());})
	                .peek(new Consumer<D>() {

						@Override
						public void accept(D t) {
							
							t.setChildrens(((TreeBaseService<D>)AopContext.currentProxy()).getChildrens(t, all));
						}
					})
	                .collect(Collectors.toList());
	 }

}
