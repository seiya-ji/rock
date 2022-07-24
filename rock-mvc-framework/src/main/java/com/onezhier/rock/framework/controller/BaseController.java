package com.onezhier.rock.framework.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.onezhier.rock.client.DTO;
import com.onezhier.rock.client.Request;
import com.onezhier.rock.client.Response;
import com.onezhier.rock.client.req.PageQuery;
import com.onezhier.rock.client.req.Query;
import com.onezhier.rock.client.resp.MultiResponse;
import com.onezhier.rock.client.resp.PageResponse;
import com.onezhier.rock.client.resp.SingleResponse;
import com.onezhier.rock.client.validator.Create;
import com.onezhier.rock.client.validator.Modify;
import com.onezhier.rock.framework.application.service.ApplicationService;
import com.onezhier.rock.framework.convertor.ApplicationConvertorI;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rocke.framework.service.BaseService;
import com.onezhier.rocke.framework.service.BaseService.CustomeConsumer;
import com.onezhier.rocke.framework.service.BaseService.Operate;

import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @Author: xxt
 * @Date: 2021/9/27 14:09
 */
@ApplicationService
public  class BaseController<LRESPD extends Response, IRESPD extends Response,CREQD extends Request, MREQD extends Request, Q extends PageQuery> implements FormValidater<CREQD> 
, ControllerI<LRESPD, IRESPD,CREQD, MREQD, Q >
{


	protected ApplicationConvertorI convertor;

	protected BaseService baseService;
	
	protected CustomeConsumer<DTO> commonFormValidator;
	
	public BaseController(BaseService<? extends DO> baseService,ApplicationConvertorI<? extends DTO,? extends DO, ? extends Query,? extends Response,? extends Response> convertor) {
		this.baseService = baseService;
		this.convertor = convertor;
	}

	
	
	@Override
	@PostMapping()
	@ApiOperation("新建资源")
	public Response create(@RequestBody @Validated(Create.class) CREQD dto) {
		if(this.commonFormValidator!=null) {
			this.commonFormValidator.accept(dto,Operate.CREATE);
		}
		this.createValidate(dto);
		this.baseService.create(this.convertor.convert(dto));
		return Response.buildSuccess();
	}

	@Override
	@PutMapping("/{id}")
	@ApiOperation("修改资源")
	public Response modify(@PathVariable("id") Long id, @RequestBody @Validated(Modify.class) MREQD dto) {
		if(this.commonFormValidator!=null) {
			this.commonFormValidator.accept(dto,Operate.MODIFY);
		}
		this.modifyValidate(id, dto);
		this.baseService.modify(id, this.convertor.convert(dto));
		return Response.buildSuccess();
	}

	@Override
	@DeleteMapping("/{id}")
	@ApiOperation("删除资源")
	public Response remove(@PathVariable("id") Long id) {
		this.baseService.remove(id);
		return Response.buildSuccess();
	}

	@Override
	@PostMapping("s")
	@ApiOperation("查询资源列表,支持分页")
	public PageResponse<LRESPD> queryPage(@RequestBody Q query) {
		Page<DO> page =  this.baseService.queryPage(query);
		return (PageResponse<LRESPD>) this.convertor.convertRespPageResponse(page);
		
	}

	@Override
	@GetMapping("/{id}")
	@ApiOperation("获取单个资源")
	public SingleResponse<IRESPD> get(@PathVariable("id") Long id) {
		DO do_ = this.baseService.get(id);
		return (SingleResponse<IRESPD>) SingleResponse.of(this.convertor.convertDetailResp(do_));

	
	}
	
	@Override
	@PostMapping(value="s",params = {"page=false"})
	@ApiOperation("查询资源列表")
	public MultiResponse<LRESPD> query(@RequestBody Q query) {
		List<DO> list =  this.baseService.query(query);
		return MultiResponse.of(this.convertor.convertRespList(list));
		
	}
	
	
	@PostMapping(value="/script")
	@ApiOperation("执行groovy脚本")
	public SingleResponse<Object> executeScript(@RequestBody Script script) {
		Object result =   this.baseService.invoke(script.getScriptName(), script.getScriptContent(), script.getParaMap());
		return SingleResponse.of(result);
		
	}
	
	
	


	
	@Data
	public static class Script {
		
		private Map<String, Object> paraMap;
		
		private String scriptContent;
		
		private String scriptName;
		
	}

}
