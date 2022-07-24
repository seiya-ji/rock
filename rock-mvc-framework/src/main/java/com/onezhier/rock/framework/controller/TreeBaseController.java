package com.onezhier.rock.framework.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.onezhier.rock.client.DTO;
import com.onezhier.rock.client.Request;
import com.onezhier.rock.client.Response;
import com.onezhier.rock.client.req.PageQuery;
import com.onezhier.rock.client.req.Query;
import com.onezhier.rock.client.resp.MultiResponse;
import com.onezhier.rock.framework.convertor.ApplicationConvertorI;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.dal.db.TreeDO;
import com.onezhier.rocke.framework.service.TreeBaseService;

import io.swagger.annotations.ApiOperation;

public class TreeBaseController<LRESPD extends Response, IRESPD extends Response,CREQD extends Request, MREQD extends Request, Q extends PageQuery> extends BaseController<LRESPD, IRESPD,CREQD, MREQD , Q >{

	public TreeBaseController(TreeBaseService<? extends TreeDO> baseService,
			ApplicationConvertorI<? extends DTO, ? extends DO, ? extends Query, ? extends Response, ? extends Response> convertor) {
		super(baseService, convertor);
	}
	
	
	@PostMapping(value="s",params = {"page=false","tree=true"})
	@ApiOperation("查询资源列表")
	public MultiResponse<LRESPD> queryTree(@RequestBody Q query) {
		List<TreeDO> list =  ((TreeBaseService)this.baseService).queryTree(query);
		return MultiResponse.of(this.convertor.convertRespList(list));
		
	}

}
