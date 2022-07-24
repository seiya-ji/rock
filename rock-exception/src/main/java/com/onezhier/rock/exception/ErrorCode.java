/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.exception;


/**
 * <p>Title: ErrorCode </p>
 * <p>Description: 通用错误码</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
public class ErrorCode {
	
	/**
	 * 系统错误
	 */
	 public static final String DEFAULT_SYS_ERR_CODE = "SYS_ERROR";
	 /**
	  * 业务异常
	  */
	 public static final String DEFAULT_BIZ_ERR_CODE = "BIZ_ERROR";
	 
	 /**
	  * 集成第三方异常，用于判定是否第三方错误
	  */
	 public static final String DEFAULT_INTEGRATE_ERR_CODE = "INTEGRATE_ERROR";
	 
	 /**
	  * 幂等异常，用于需要幂等的接口
	  */
	 public static final String DEFAULT_IDEMPOTENT_ERR_CODE = "IDEMPOTENT_ERROR";
	 
	 /**
	  * 服务超时，可能己完成，需要做幂等重试。
	  */
	 public static final String DEFAULT_SYS_ERR_TIME_OUT_CODE = "SYS_ERROR_TIME_OUT";
	 
	 /**
	  * 服务提供者过载拒绝服务，可做幂等重试，也可直接返回，让用户重试。
	  */
	 public static final String DEFAULT_SYS_ERR_PROVIDER_REJECTED_CODE = "SYS_ERROR_PROVIDER_REJECTED";
	 
	 
	 /**
	  * 服务消费者调用多次失败拒绝服务，可做幂等重试，也可直接返回，让用户重试。
	  */
	 public static final String DEFAULT_SYS_ERR_COMSUMER_REJECTED_CODE = "SYS_ERROR_COMSUMER_REJECTED";
	 
	 
	 /**
	  * 服务消费者调用第三方失败，
	  */
	 public static final String DEFAULT_SYS_ERR_RPC_CODE = "SYS_ERROR_RPC";
	 

}
