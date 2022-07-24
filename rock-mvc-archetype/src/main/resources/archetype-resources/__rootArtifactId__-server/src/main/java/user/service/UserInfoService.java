#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2022 All Rights Reserved.
 */
package ${package}.user.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onezhier.rock.exception.BizException;
import com.onezhier.rock.framework.application.UserContextHolder;
import com.onezhier.rocke.framework.service.BaseService;
import ${package}.user.convertor.UserInfoConvertor;
import ${package}.user.dao.entity.UserInfo;
import ${package}.user.dao.repository.UserInfoRepository;
import ${package}.user.dto.enums.UserJobStatus;

/**
 * @author jihb
 * @date 2022年06月01日 11:33
 */

@Transactional(rollbackFor = RuntimeException.class)
@Service
public class UserInfoService extends BaseService<UserInfo> {


    @Autowired
    public UserInfoService(UserInfoRepository respository, UserInfoConvertor convertor) {
		super(respository, convertor);
	}


	
	

	
   
   
   

}
