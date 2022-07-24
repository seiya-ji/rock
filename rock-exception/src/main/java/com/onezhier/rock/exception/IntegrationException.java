/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.exception;

/**
 * <p>Title: IntegrationException </p>
 * <p>Description: 与第三方集成的异常</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
public class IntegrationException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = ErrorCode.DEFAULT_INTEGRATE_ERR_CODE;

    public IntegrationException(String errMessage) {
        super(DEFAULT_ERR_CODE, errMessage);
    }

    public IntegrationException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public IntegrationException(String errMessage, Throwable e) {
        super(DEFAULT_ERR_CODE, errMessage, e);
    }

    public IntegrationException(String errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }

}