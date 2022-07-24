#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.user.convertor;

import java.util.List;

import org.mapstruct.Mapper;

import com.onezhier.rock.framework.convertor.ApplicationConvertorI;
import ${package}.user.dao.entity.UserInfo;
import ${package}.user.dto.req.UserInfoReqDTO;
import ${package}.user.dto.req.query.UserInfoQuery;
import ${package}.user.dto.resp.UserInfoRespDTO;
import ${package}.user.dto.resp.UserInfoRespListDTO;

/**
 * @author jihb
 * @date 2022年06月01日 11:33
 */

@Mapper(componentModel = "spring")
public interface UserInfoConvertor extends ApplicationConvertorI<UserInfoReqDTO, UserInfo, UserInfoQuery, UserInfoRespListDTO, UserInfoRespDTO>{

	
}
