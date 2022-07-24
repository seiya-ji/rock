/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application;

import com.onezhier.rock.framework.application.UserContext.UserInfo;

/**
 * <p>Title: UserInfoService </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
public interface UserInfoService {

	public UserInfo getUser(Object... args);
	
}
