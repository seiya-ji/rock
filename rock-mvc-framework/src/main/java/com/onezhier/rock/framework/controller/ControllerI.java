package com.onezhier.rock.framework.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import com.onezhier.rock.client.Request;
import com.onezhier.rock.client.Response;
import com.onezhier.rock.client.req.PageQuery;
import com.onezhier.rock.client.resp.MultiResponse;
import com.onezhier.rock.client.resp.PageResponse;
import com.onezhier.rock.client.resp.SingleResponse;
import com.onezhier.rock.client.validator.Modify;

public interface ControllerI <LRESPD extends Response, IRESPD extends Response,CREQD extends Request, MREQD extends Request, Q extends PageQuery>{

	
	public Response create( CREQD dto) ;

	public Response modify( Long id, @RequestBody @Validated(Modify.class) MREQD dto);

	public Response remove( Long id) ;

	public PageResponse<LRESPD> queryPage(@RequestBody Q query) ;

	public SingleResponse<IRESPD> get( Long id) ;
	

	public MultiResponse<LRESPD> query( Q query) ;
	
	
}
