/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.exception;

/**
 * <p>Title: FallbackException </p>
 * <p>Description: 需要的降级异常</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
public class FallbackException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = ErrorCode.DEFAULT_SYS_ERR_PROVIDER_REJECTED_CODE;

    public FallbackException(String errMessage) {
        super(DEFAULT_ERR_CODE, errMessage);
    }

    public FallbackException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public FallbackException(String errMessage, Throwable e) {
        super(DEFAULT_ERR_CODE, errMessage, e);
    }

    public FallbackException(String errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }

}
