#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.user.dto.req.query;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.onezhier.rock.client.req.PageQuery;
import ${package}.user.dto.enums.UserJobStatus;

import lombok.Data;

/**
 * @author jihb
 * @date 2022年06月01日 11:33
 */
@Data
public class UserInfoQuery extends PageQuery{

	private Long tenantId;

	 private Long deptId;
	 
    private Long roleId;
    
    @JsonSetter(nulls = Nulls.SKIP)
    private UserJobStatus state;

  
	
}
