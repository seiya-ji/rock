/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */ 
package com.onezhier.rock.exception;

/**
 * <p>Title: WebException </p>
 * <p>Description: WebException is known Exception, no need retry</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
public class WebException extends BaseException {

    protected static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = ErrorCode.DEFAULT_BIZ_ERR_CODE;

    public WebException(String errMessage) {
        super(DEFAULT_ERR_CODE, errMessage);
    }

    public WebException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public WebException(String errMessage, Throwable e) {
        super(DEFAULT_ERR_CODE, errMessage, e);
    }

    public WebException(String errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }

}