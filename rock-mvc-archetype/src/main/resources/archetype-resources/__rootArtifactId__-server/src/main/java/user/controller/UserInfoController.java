#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2022 All Rights Reserved.
 */

package ${package}.user.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onezhier.rock.client.resp.MultiResponse;
import com.onezhier.rock.client.resp.SingleResponse;
import com.onezhier.rock.framework.controller.BaseController;
import ${package}.user.convertor.UserInfoConvertor;
import ${package}.user.service.UserInfoService;
import ${package}.user.dto.req.UserInfoReqDTO;
import ${package}.user.dto.req.query.UserInfoQuery;
import ${package}.user.dto.resp.UserInfoRespDTO;
import ${package}.user.dto.resp.UserInfoRespListDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author jihb
 * @date 2022年06月01日 11:33
 */

@RestController
@RequestMapping("/manager/user")
@Api(tags = "用户接口")
public class UserInfoController extends BaseController<UserInfoRespListDTO, UserInfoRespDTO, UserInfoReqDTO, UserInfoReqDTO, UserInfoQuery>{

	@Autowired
    public UserInfoController(UserInfoService baseService,
			UserInfoConvertor convertor) {
		super(baseService, convertor);
	}



	
	

}
