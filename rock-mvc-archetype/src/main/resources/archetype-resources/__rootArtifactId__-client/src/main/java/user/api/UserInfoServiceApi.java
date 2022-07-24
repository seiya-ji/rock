#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onezhier.rock.client.Response;
import com.onezhier.rock.client.resp.SingleResponse;

import ${package}.user.dto.resp.UserInfoRespDTO;
import ${package}.user.dto.resp.UserInfoRespListDTO;

import io.swagger.annotations.ApiOperation;

/**
 * @author jihb
 * @date 2022年06月01日 11:33
 */

@FeignClient(name = "user-service",url = "http://127.0.0.1:8081", path = "/manager/user")
public interface UserInfoServiceApi {

	

	
	@GetMapping("/{id}")
	@ApiOperation("获取单个资源")
	public SingleResponse<UserInfoRespDTO> get(@PathVariable("id") Long id) ;

}
