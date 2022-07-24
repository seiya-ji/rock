#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */
package ${package}.user.dto.resp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.onezhier.rock.client.Response;
import ${package}.user.dto.enums.UserJobStatus;

import lombok.Data;

/**
 * @author jihb
 * @date 2022年06月01日 11:33
 */
@Data
public class UserInfoRespDTO extends Response{
	
	private Long id;
	
	private Long tenantId;
	
	 private String name;

	  private String fullName;

	  private String phone;

	  private String email;

	  private Long roleId;

	  private String roleName;

	  private Date createTime;

	  private Date lastModifyTime;

	   private UserJobStatus state;

	  private String password;

	  private Long deptId;
	  
	  private String deptName;

	  private Set<String> roles = new HashSet();

}