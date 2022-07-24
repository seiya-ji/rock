package com.onezhier.rocke.framework.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.transaction.annotation.Transactional;

import com.onezhier.rock.client.req.PageQuery;
import com.onezhier.rock.client.req.Query;
import com.onezhier.rock.exception.BizException;
import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.framework.application.UserContextHolder;
import com.onezhier.rock.framework.application.service.ServiceContextHolder;
import com.onezhier.rock.framework.common.FrameworkBaseUtils;
import com.onezhier.rock.framework.convertor.ApplicationConvertorI;
import com.onezhier.rock.framework.dal.BaseRespository;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.utils.FrameworkBeanUtils;
import com.onezhier.rock.protocol.annotation.AssociationRelation;
import com.onezhier.rock.protocol.annotation.AssociationRelation.Amount;
import com.onezhier.rock.protocol.annotation.AssociationRelation.Association;
import com.onezhier.rock.protocol.annotation.CreateType;
import com.onezhier.rock.protocol.annotation.LogicConstraint;
import com.onezhier.rock.protocol.annotation.Method;
import com.onezhier.rock.protocol.annotation.Model;
import com.onezhier.rock.protocol.annotation.ReferConstraint;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.Tuple2;
import lombok.Builder;
import lombok.Data;

/**
 * 服务基础类，具有一般的curd功能，
 * 通过验证器，业务处理器，值设置器支持特殊的，复杂的curd的场景
 * @author jihb
 *
 * @param <D> 数据模型类型
 */
@Transactional(rollbackFor = RuntimeException.class)
public class BaseService<D extends DO> implements BusinessValidater,DOSettor<D>,BusinessHandler<D>,ApplicationContextAware {
	
	@FunctionalInterface
	public static interface CustomeConsumer<T>{
		
		
		 void accept(T t, Operate operate);
		 
		
	}
	
	@FunctionalInterface
	public static interface CustomeSettorConsumer<T,R>{
		
		
		 void accept(T t,R r, Operate operate);
		 
		
	}
	
	
	public enum Operate {
		CREATE,MODIFY,REMOVE;
	}
	
	protected  BaseRespository respository;
	
	protected ApplicationConvertorI convertor;
	
	
	//通用的业务验证器，对各个业务有效
	protected CustomeConsumer<DO>  commonBusinessValidator;
	
	//通用的值设置器，对各个业务有效
	protected CustomeSettorConsumer<D,D>  commonSettor;
	
	//唯一验证器，对创建，修改业务有效
	protected CustomeConsumer<DO>  uniqueValidator;
	
	//引用验证器，对创建，修改业务有效
	protected CustomeConsumer<DO>  referValidator;
	
	//逻辑验证器，对创建，修改，删除业务有效
	protected CustomeConsumer<DO>  logicValidator;
	
	private ApplicationContext applicationContext;
	
	@Value("${application.enableUniqueValidator:true}")
	protected Boolean enableUniqueValidator=true;
    
	@Value("${application.enableReferValidator:true}")
	protected Boolean enableReferValidator=true; 
	
	@Value("${application.enableLogicValidator:true}")
	protected Boolean enableLogicValidator=true; 
	
	@Value("${application.enableAssociation:true}")
	protected Boolean enableAssociation=true; 
	
	private ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
	
	public Object invokeScripte(String scriptName,String scripteContent,Map<String, Object> params) {
		
		Bindings  bindings = this.engine.createBindings();
		bindings.put("applicationContext", this.applicationContext);
		bindings.put("currentUser", UserContextHolder.get().getUserInfo());
		bindings.put("currentService", this);
		bindings.put("serviceContext", ServiceContextHolder.get());
	
		try {
			
			this.engine.eval(scripteContent, bindings);
			
			 Invocable invocable = (Invocable) this.engine;
			 return  invocable.invokeFunction(scriptName,params.values());
			 
		} catch (ScriptException | NoSuchMethodException e) {
			throw new SysException(e.getMessage(),e);
		}
		
	}
	
	
	public Object invoke(String scriptName,String scripteContent,Map<String, Object> params)  {
		  CompilerConfiguration config = new CompilerConfiguration();
	        config.setSourceEncoding("UTF-8");
	        // 设置该GroovyClassLoader的父ClassLoader为当前线程的加载器(默认)
	        GroovyClassLoader  groovyClassLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
	        Class<?> groovyClass = groovyClassLoader.parseClass(scripteContent);
	    try {    
	        GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
	        Object methodResult = groovyObject.invokeMethod(scriptName, params.values());
           return methodResult;
	} catch (Exception e) {
		throw new SysException(e.getMessage(),e);
	}

	}
	
	public BaseService(BaseRespository<? extends DO, ? extends  Serializable> respository, ApplicationConvertorI convertor) {
		this.respository = respository;
		this.convertor = convertor;
		
		if(this.enableUniqueValidator) {
			//唯一验证器
			this.uniqueValidator = (d,o)->{
				List<Tuple2<String[],String>> uniqueAttrList =   FrameworkBaseUtils.getUniqueConstraints(d);
				uniqueAttrList.forEach(new Consumer<Tuple2<String[],String>>() {

					@Override
					public void accept(Tuple2<String[],String> tuple) {
						DO do_ = FrameworkBaseUtils.constractInstance(d, tuple.getV1());
						do_.clearInitData();
						Optional<DO> optional = BaseService.this.respository.findOne(Example.of(do_));
						if( o==Operate.CREATE) {
							if (optional.isPresent()) {
						         throw new BizException(tuple.getV2()+" -> "+StringUtils.join(tuple.getV1(), ','));
						     }
						}
						
						if(o==Operate.MODIFY) {
							if (optional.isPresent()&&!optional.get().getId().equals(d.getId())) {
						         throw new BizException(tuple.getV2()+" -> "+StringUtils.join(tuple.getV1(), ','));
						     }
						}
					}
				});
		
			};
		}
		
		if(this.enableReferValidator) {
			//引用验证器
			this.referValidator = (d,o)->{
				if(o==Operate.CREATE||o==Operate.MODIFY) {
					
					 List<ReferConstraintInfo> contraintList =	this.getReferConstraintInfos(d);
					  for(ReferConstraintInfo contraint : contraintList) {
						  	BaseService otherBaseService = FrameworkBeanUtils.getBaseService(contraint.getOtherClass(), applicationContext);
							Map<String, Object> attrMap = new HashMap<String, Object>();
							for(int i = 0;i<contraint.getOtherAttrNames().length;i++) {
								attrMap.put(contraint.getOtherAttrNames()[i], contraint.getSelfAttrValues()[i]);
							}
							
							DO exp = FrameworkBaseUtils.constractInstance(contraint.getOtherClass(), attrMap);
							exp.clearInitData();
							List<DO> doList =  otherBaseService.query(exp);
							if(doList.size()==0) {
								throw new BizException(contraint.getPrompt()+" -> "+StringUtils.join(contraint.getOtherAttrNames(), ','));
							}
							
							
					  }
				}
			 
			};
				
		}
		
		if(this.enableLogicValidator) {
			//引用验证器
			this.logicValidator = (d,o)->{
				if(o==Operate.CREATE||o==Operate.MODIFY||o==Operate.REMOVE) {
					
					 List<LogicConstraintInfo> contraintList =	this.getLogicConstraintInfos(d);
					  for(LogicConstraintInfo contraint : contraintList) {
						  List<DO> otherList = null; 
						  if(contraint.getOtherClass()!=null&&StringUtils.isNotBlank( contraint.getOtherAttrName())&&StringUtils.isNotBlank( contraint.getSelfAttrName())) {
								BaseService otherBaseService = FrameworkBeanUtils.getBaseService(contraint.getOtherClass(), applicationContext);
								Map<String, Object> attrMap = new HashMap<String, Object>();
								attrMap.put(contraint.getOtherAttrName(), contraint.getSelfAttrValue());
								DO exp = FrameworkBaseUtils.constractInstance(contraint.getOtherClass(), attrMap);
								exp.clearInitData();
								otherList =  otherBaseService.query(exp);
								
						  }
						  
							  	String template = contraint.getExpression();//设置文字模板,其中#{}表示表达式的起止，#user是表达式字符串，表示引用一个变量。
						        ExpressionParser paser = new SpelExpressionParser();//创建表达式解析器
	
						        //通过evaluationContext.setVariable可以在上下文中设定变量。
						        EvaluationContext context = new StandardEvaluationContext();
						        context.setVariable("self",d);
						        context.setVariable("other",otherList);
					        try {
						        //解析表达式，如果表达式是一个模板表达式，需要为解析传入模板解析器上下文。
						        Expression expression = paser.parseExpression(template,new TemplateParserContext());
	
						        //使用Expression.getValue()获取表达式的值，这里传入了Evalution上下文，第二个参数是类型参数，表示返回值的类型。
						        Boolean isPass = expression.getValue(context,Boolean.class);
						        if(!isPass) {
						        	throw new BizException(contraint.getPrompt()+" -> "+contraint.getOtherAttrName());
						        }
					        }catch (ParseException | EvaluationException e) {
								throw new SysException(e.getMessage(),e);
							}
							
							
					  }
				}
			 
			};
				
		}
		
		
		
		
		
		
	}
	
	
	@Method(alias = "批量新建",comment = "批量新建资源",exposed = true, classify  = CreateType.DEFAULT)
	public List<D> createBatch(List<D> doList){
		List<D> result = new LinkedList<>();
		this.preCreateBatch(doList, applicationContext);
		for(D do_ : doList) {
			result.add(((BaseService<D>)AopContext.currentProxy()).create(do_));
		}
		this.postCreateBatch(result, applicationContext);
		
		return result;
	}
	
	@Method(alias = "新建",comment = "新建资源",exposed = true, classify  = CreateType.DEFAULT)
	public D create(D do_) {
		
		if(this.uniqueValidator!=null) {
			this.uniqueValidator.accept(do_,Operate.CREATE);
		}
		
		
		if(this.referValidator!=null) {
			this.referValidator.accept(do_,Operate.CREATE);
		}
		
		if(this.logicValidator!=null) {
			this.logicValidator.accept(do_,Operate.CREATE);
		}
		
		if(this.commonBusinessValidator!=null) {
			this.commonBusinessValidator.accept(do_,Operate.CREATE);
		}
		
		
		((BaseService<D>)AopContext.currentProxy()).createValidate(do_);
		
		if(this.commonSettor!=null) {
			this.commonSettor.accept(null,do_,Operate.CREATE);
		}
		
		Model  entity =  FrameworkBaseUtils.getEntityAnno(do_);
		if(entity!=null&&entity.autoAudit()) {
			do_.setCreateTime(new Date());
			do_.setCreateUserId(UserContextHolder.get().getUserInfo().getLongId());
			do_.setCreateUserName(UserContextHolder.get().getUserInfo().getName());
		}
		
		((BaseService<D>)AopContext.currentProxy()).createSet(do_);
		D newDO =  (D) this.respository.save(do_);
		((BaseService<D>)AopContext.currentProxy()).postCreate(newDO, this.applicationContext);
		return (D) newDO;
	}
	
	protected String[] notModifyAttrs() {
		return new  String[0];
	}
	
	@Method(alias = "编辑",comment = "编辑资源",exposed = true, classify = CreateType.DEFAULT)
	public D modify(Long id ,D newDo ) {
		
		D oldDo  = (D) this.respository.getOne(id);
		if(oldDo == null) {
			throw new BizException("未找到對應的實體");
		}
		newDo.setId(id);
		
		if(this.uniqueValidator!=null) {
			this.uniqueValidator.accept(newDo,Operate.MODIFY);
		}
		
		if(this.referValidator!=null) {
			this.referValidator.accept(newDo,Operate.MODIFY);
		}
		
		if(this.logicValidator!=null) {
			this.logicValidator.accept(newDo,Operate.CREATE);
		}
		
		if(this.commonBusinessValidator!=null) {
			this.commonBusinessValidator.accept(newDo,Operate.MODIFY);
		}
		((BaseService<D>)AopContext.currentProxy()).modifyValidate(oldDo,newDo);
		if(this.commonSettor!=null) {
			this.commonSettor.accept((D)oldDo,newDo,Operate.MODIFY);
		}
		String[] defaultAttrs =  new String[] {"id", "updateTime","createTime","createUserName","createUserId","updateUserName","updateUserId","v","visiable"};
	
		BeanUtils.copyProperties(newDo, oldDo,(String[])ArrayUtils.addAll(defaultAttrs, this.notModifyAttrs()));
			
		Model  entity =  FrameworkBaseUtils.getEntityAnno(oldDo);
		if(entity!=null&&entity.autoAudit()) {
			oldDo.setUpdateTime(new Date());
			oldDo.setUpdateUserId(UserContextHolder.get().getUserInfo().getLongId());
			oldDo.setUpdateUserName(UserContextHolder.get().getUserInfo().getName());
		}
		((BaseService<D>)AopContext.currentProxy()).modifySet((D)oldDo, newDo);
		
		this.respository.save(oldDo);
		
		((BaseService<D>)AopContext.currentProxy()).postModify(oldDo,newDo, this.applicationContext);
		return (D) oldDo;
	}
	
	@Method(alias = "批量编辑",comment = "批量编辑资源",exposed = true, classify = CreateType.DEFAULT)
	public List<D> modifyBatch(List<Long> ids ,D newDo ) {
		List<D> result = new LinkedList<D>();
		for(Long id : ids) {
			result.add(this.modify(id, newDo));
		}
		return result;
	}

	@Method(alias = "删除",comment = "删除资源",exposed = true, classify = CreateType.DEFAULT)
	public D remove(Long id) {
		D oldDo  = (D)this.respository.getOne(id);
		if(oldDo == null) {
			throw new BizException("未找到對應的實體");
		}
		
		if(this.logicValidator!=null) {
			this.logicValidator.accept(oldDo,Operate.CREATE);
		}
		
		if(this.commonBusinessValidator!=null) {
			this.commonBusinessValidator.accept(oldDo, Operate.REMOVE);
		}
		
		
		((BaseService<D>)AopContext.currentProxy()).removeValidate(oldDo);
		Model  entity =  FrameworkBaseUtils.getEntityAnno(oldDo);
		if(entity==null||!entity.logic()) {
			this.respository.delete(oldDo);
		}else {
			oldDo.setVisiable(0);
			this.respository.save(oldDo);
		}
		//删除关联相关的
		if(this.enableAssociation) {
			((BaseService<D>)AopContext.currentProxy()).removeAssociation(oldDo);
		}
		
		
		((BaseService<D>)AopContext.currentProxy()).postRemove(oldDo, this.applicationContext);
		
		return oldDo;
	
	}
	
	@Data
	@Builder
	private static class AssociationInfo{
		
	    
	    private Boolean mapTooMany;
	    
	    
	    private Association associationType;
	    
	    private Class sourceClass;
	    
	    private Class targetClass;
	    
	    private String targetAttrName;
	    
	    private Object targetAttrValue;
	    
	    private String sourceAttrName;
	    
	    private Object sourceAttrValue;
	}
	
	@Data
	@Builder
	private static class  ReferConstraintInfo{
		
	   private String[] selfAttrNames;
	   
	   private Object[] selfAttrValues;
	   
	   private Class otherClass;
	   
	   private String[] otherAttrNames;
	   
	   private String prompt;
	   
	}
	
	
	@Data
	@Builder
	private static class  LogicConstraintInfo{
		
	   private String selfAttrName;
	   
	   private Object selfAttrValue;
	   
	   private Class otherClass;
	   
	   private String otherAttrName;
	   
	   private String expression;
	   
	   private String prompt;
	   
	}
	
	private List<LogicConstraintInfo> getLogicConstraintInfos(DO do_){
		List<LogicConstraintInfo>  result = new LinkedList<>();
		Model  entity =  FrameworkBaseUtils.getEntityAnno(do_);
		if(entity==null) {
			return result;
		}
		LogicConstraint[] constraintList = entity.logicConstraints();
		if(constraintList.length==0) {
			return result;
		}
		for(LogicConstraint constraint :constraintList) {
			Object  selfAttrValue =  FrameworkBaseUtils.getValByMethod(do_, constraint.selfAttrName());
			LogicConstraintInfo info = LogicConstraintInfo.builder().otherAttrName(constraint.otherAttrName()).otherClass(constraint.otherClass())
					.prompt(constraint.prompt()).selfAttrName(constraint.selfAttrName()).selfAttrValue(selfAttrValue).expression(constraint.expression()).build();
			result.add(info);
		}
		return result;
	}
	
	private List<ReferConstraintInfo> getReferConstraintInfos(DO do_){
		List<ReferConstraintInfo>  result = new LinkedList<>();
		Model  entity =  FrameworkBaseUtils.getEntityAnno(do_);
		if(entity==null) {
			return result;
		}
		ReferConstraint[] constraintList = entity.referConstraints();
		if(constraintList.length==0) {
			return result;
		}
		for(ReferConstraint constraint :constraintList) {
			if(constraint.selfAttrNames().length!=constraint.otherAttrNames().length) {
				throw new SysException("配置不正确，数量不对应！");
			}
			Object[] selfAttrValues = new Object[constraint.selfAttrNames().length];
			String[] otherAttrNames = new String[constraint.selfAttrNames().length];
			for(int i =0;i<constraint.otherAttrNames().length;i++) {
				String otherAttrName = constraint.otherAttrNames()[i];
				otherAttrNames[i] = otherAttrName;
				Object selfAttrValue = null;
				if(!otherAttrName.contains("=")) {
					selfAttrValue =FrameworkBaseUtils.getValByMethod(do_, constraint.selfAttrNames()[i]);
				}else {
					selfAttrValue = otherAttrName.split("=")[1];
					otherAttrNames[i] = otherAttrName.split("=")[0];
				}
				selfAttrValues[i] =  selfAttrValue;
			}
			
			ReferConstraintInfo info = ReferConstraintInfo.builder().otherAttrNames(otherAttrNames).otherClass(constraint.otherClass())
					.prompt(constraint.prompt()).selfAttrNames(constraint.selfAttrNames()).selfAttrValues(selfAttrValues).build();
			result.add(info);
		}
		return result;
	}
	
	private List<AssociationInfo> getAssociationInfos(DO do_){
		List<AssociationInfo>  result = new LinkedList<>();
		Model  entity =  FrameworkBaseUtils.getEntityAnno(do_);
		if(entity==null) {
			return result;
		}
		AssociationRelation[] relationList = entity.associationRelationes();
		if(relationList.length==0) {
			return result;
		}
		for(AssociationRelation relation :relationList) {
			Object  obj =  FrameworkBaseUtils.getValByMethod(do_, relation.selfAttrName());
			AssociationInfo info = AssociationInfo.builder().associationType(relation.associationType()).sourceClass(do_.getClass()).sourceAttrName(relation.selfAttrName()).sourceAttrValue(obj)
			.targetClass(relation.otherClass()).targetAttrName(relation.otherAttrName()).mapTooMany(relation.otherAmount()==Amount.MANY?true:false).build();
			result.add(info);
		}
		return result;
	}
	
	/**
	 * 删除该实体相关联的实体
	 * @param oldDo
	 */
	public void removeAssociation (DO oldDo) {
		
		List<AssociationInfo>  relationList =  this.getAssociationInfos(oldDo);
		
		if(relationList.size()==0) {
			return ;
		}
		
		for(AssociationInfo relation :relationList) {
			BaseService otherBaseService = FrameworkBeanUtils.getBaseService(relation.getTargetClass(), applicationContext);
			Map<String, Object> attrMap = new HashMap<String, Object>();
			attrMap.put(relation.getTargetAttrName(), relation.getSourceAttrValue());
			DO exp = FrameworkBaseUtils.constractInstance(relation.getTargetClass(), attrMap);
			exp.clearInitData();
			List<DO> doList =  otherBaseService.query(exp);
			for(DO do_ : doList) {
				otherBaseService.remove(do_.getId());
			}
			
		}
	
	}
	
	
	@Method(alias = "批量删除",comment = "批量删除资源",exposed = true, classify = CreateType.DEFAULT)
	public List<D> removeBatch(List<Long> ids) {
		List<D> result = new LinkedList<>();
		for(Long id : ids) {
			result.add(this.remove(id));
		}
		((BaseService<D>)AopContext.currentProxy()).postRemoveBatch(result, applicationContext);
		return result;
	}

	@Method(alias = "查看",comment = "查看某一资源",exposed = true, classify = CreateType.DEFAULT)
	@Transactional(readOnly = true)
	public D get(Long id) {
		
		Optional<DO>  optional = (Optional<DO>) this.respository.findById(id);
		if(!optional.isPresent()) {
			return null;
		}
		D do_ = (D) optional.get();
		this.postGet(do_, applicationContext);
		return do_;
	}
	
	@Method(alias = "查找",comment = "查找资源，支持分页",exposed = true, classify = CreateType.DEFAULT)
	@Transactional(readOnly = true)
	public   Page<D>  queryPage(PageQuery query){
		Sort sort = Sort.unsorted();
		if(StringUtils.isNotBlank(query.getOrderBy())) {
			sort = Sort.by(Direction.fromString(query.getOrderDirection()), query.getOrderBy());
		}
		Pageable pageable = PageRequest.of(query.getPageIndex()-1, query.getPageSize(), sort);
		

		
		Page<DO> page = this.respository.findAll(this.assertSpecification(query), pageable);
		page.getContent().stream().forEach((t)->{
			this.postGet((D)t, BaseService.this.applicationContext);
		});
		return  (Page<D>) page;
		
	}
	
	
	private Specification<D> assertSpecification(Query query){
		Tuple2<D,Specification<D>> tuple =  this.convertor.convertQuerySpecification(query);
		Model  entity =  FrameworkBaseUtils.getEntityAnno(tuple.getV1());
		if(entity!=null&&entity.logic()) {
			tuple.getV2().and(new Specification<D>() {

				@Override
				public Predicate toPredicate(Root<D> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
					return builder.equal(root.get("visiable"), 1);
				}
			});
		}
		return tuple.getV2();
	}
	
	@Method(alias = "查找",comment = "查找资源，不分页",exposed = true, classify = CreateType.DEFAULT)
	@Transactional(readOnly = true)
	public   List<D>  query(Query query){
		
		List<DO> list = this.respository.findAll(this.assertSpecification(query));
		list.stream().forEach((t)->{
			this.postGet((D)t, BaseService.this.applicationContext);
		});
		return  (List<D>) list;
		
	}
	
	@Transactional(readOnly = true)
	public   List<D>  query(DO query){
		List<DO> list = this.respository.findAll(Example.of(query));
		return  (List<D>) list;
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
