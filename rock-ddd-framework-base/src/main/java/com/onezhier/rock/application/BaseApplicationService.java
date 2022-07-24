package com.onezhier.rock.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.onezhier.rock.application.common.FrameworkUtils;
import com.onezhier.rock.application.executor.BaseResourceAddCmdExe;
import com.onezhier.rock.application.executor.BaseResourceModifyCmdExe;
import com.onezhier.rock.application.executor.BaseResourceRemoveCmdExe;
import com.onezhier.rock.application.executor.query.BaseResourceGetQueryExe;
import com.onezhier.rock.application.executor.query.BaserResourceQueryExe;
import com.onezhier.rock.client.ApplicationServiceI;
import com.onezhier.rock.client.Response;
import com.onezhier.rock.client.req.Command;
import com.onezhier.rock.client.req.PageQuery;
import com.onezhier.rock.client.resp.PageResponse;
import com.onezhier.rock.client.resp.SingleResponse;
import com.onezhier.rock.framework.application.service.ApplicationService;
import com.onezhier.rock.framework.application.service.ServiceContextHolder;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.domain.EntityObject;

import io.swagger.annotations.ApiOperation;

@ApplicationService
public  class BaseApplicationService<LRESPD extends Response, IRESPD extends Response, AC extends Command, MC extends Command, Q extends PageQuery>
		implements ApplicationServiceI {

	@Autowired
	private BaseResourceAddCmdExe baseResourceCreateCmdExe;

	@Autowired
	private BaseResourceModifyCmdExe baseResourceModifyCmdExe;

	@Autowired
	private BaseResourceRemoveCmdExe baseResourceRemoveCmdExe;

	@Autowired
	private BaseResourceGetQueryExe baseResourceGetQueryExe;

	@Autowired
	private BaserResourceQueryExe baserResourceQueryExe;

	@PostMapping()
	@ApiOperation("新建资源")
	public Response add(@RequestBody AC cmd) {
		return this.baseResourceCreateCmdExe.execute(cmd, ServiceContextHolder.get().getEntityClass());
	}

	@PutMapping("/{id}")
	@ApiOperation("修改资源")
	public Response modify(@PathVariable("id") Long id, @RequestBody MC cmd) {
		ServiceContextHolder.get().getContain().put(ServiceContextHolder.get().getEntityClass().getName(), id);
		return this.baseResourceModifyCmdExe.execute(cmd, ServiceContextHolder.get().getEntityClass());
	}

	@DeleteMapping("/{id}")
	@ApiOperation("删除资源")
	public Response remove(@PathVariable("id") Long id) {
		return this.baseResourceRemoveCmdExe.execute(new BaseResourceRemoveCmdExe.BaseResourceRemoveCmd(id),
				ServiceContextHolder.get().getEntityClass());
	}

	@PostMapping("s")
	@ApiOperation("查询资源列表")
	public PageResponse<LRESPD> query(@RequestBody Q query) {
		return (PageResponse<LRESPD>) this.baserResourceQueryExe.execute(query,
				ServiceContextHolder.get().getListDtoClass());
	}

	@GetMapping("/{id}")
	@ApiOperation("获取单个资源")
	public SingleResponse<IRESPD> get(@PathVariable("id") Long id) {
		return this.baseResourceGetQueryExe.execute(new BaseResourceGetQueryExe.BaseResourceGetQueryCmd(id),
				ServiceContextHolder.get().getDtoClass());
	}
	
	private Class<? extends DO> getDOClass (Class<? extends EntityObject> entityClz , Class<? extends DO> defaultDOClz) {
		 if(defaultDOClz != DO.class) {
			 return defaultDOClz;
		 } 
		return FrameworkUtils.getDOClazz(entityClz);
		
		
	}

}
