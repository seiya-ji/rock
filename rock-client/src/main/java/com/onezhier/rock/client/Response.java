/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.client;


/**
 * <p>Title: Response </p>
 * <p>Response to caller</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
public class Response extends DTO {

    protected static final long serialVersionUID = 1L;

    private boolean success;

    private String errCode;

    private String errMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    @Override
    public String toString() {
        return "Response [success=" + success + ", errCode=" + errCode + ", errMessage=" + errMessage + "]";
    }

    public static Response buildSuccess() {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    public static Response buildFailure(String errCode, String errMessage) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }
}
