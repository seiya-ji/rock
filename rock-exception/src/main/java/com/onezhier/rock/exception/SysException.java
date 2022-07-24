/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.exception;

/**
 * <p>Title: SysException </p>
 * <p>Description: System Exception is unexpected Exception, retry might work again</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
public class SysException extends BaseException {

    private static final long serialVersionUID = 4355163994767354840L;

    private static final String DEFAULT_ERR_CODE = ErrorCode.DEFAULT_SYS_ERR_CODE;

    public SysException(String errMessage) {
        super(DEFAULT_ERR_CODE, errMessage);
    }

    public SysException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public SysException(String errMessage, Throwable e) {
        super(DEFAULT_ERR_CODE, errMessage, e);
    }

    public SysException(String errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }

}
