/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.exception;

/**
 * <p>Title: IdempotentException </p>
 * <p>Description: 幂等异常，该异常返回调用方，告知其可以再次调用</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
public class IdempotentException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = ErrorCode.DEFAULT_IDEMPOTENT_ERR_CODE;

    public IdempotentException(String errMessage) {
        super(DEFAULT_ERR_CODE, errMessage);
    }

    public IdempotentException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public IdempotentException(String errMessage, Throwable e) {
        super(DEFAULT_ERR_CODE, errMessage, e);
    }

    public IdempotentException(String errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }

}