/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.exception;

/**
 * <p>Title: InfrastructureException </p>
 * <p>Description: 基础层最高异常，基础层所发生的异常都来自于此，sysexception除外</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
public class InfrastructureException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = ErrorCode.DEFAULT_BIZ_ERR_CODE;

    public InfrastructureException(String errMessage) {
        super(DEFAULT_ERR_CODE, errMessage);
    }

    public InfrastructureException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public InfrastructureException(String errMessage, Throwable e) {
        super(DEFAULT_ERR_CODE, errMessage, e);
    }

    public InfrastructureException(String errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }

}