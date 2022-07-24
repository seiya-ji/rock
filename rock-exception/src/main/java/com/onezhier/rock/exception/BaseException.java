/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.exception;


/**
 * <p>Title: BaseException </p>
 * <p>Description: Base Exception is the parent of all exceptions</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errCode;

    public BaseException(String errMessage) {
        super(errMessage);
    }

    public BaseException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
    }

    public BaseException(String errMessage, Throwable e) {
        super(errMessage, e);
    }

    public BaseException(String errCode, String errMessage, Throwable e) {
        super(errMessage, e);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

}
