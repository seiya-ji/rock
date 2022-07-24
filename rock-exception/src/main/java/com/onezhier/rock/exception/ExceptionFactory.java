/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.exception;

/**
 * <p>Title: ExceptionFactory </p>
 * <p>Description: 异常工厂实现</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
public class ExceptionFactory {

    public static BizException bizException(String errorMessage) {
        return new BizException(errorMessage);
    }

    public static BizException bizException(String errorCode, String errorMessage) {
        return new BizException(errorCode, errorMessage);
    }
    
    public static BizException bizException(IntegrationException e) {
        return new BizException( e.getMessage(),e);
    }

    public static SysException sysException(String errorMessage) {
        return new SysException(errorMessage);
    }

    public static SysException sysException(String errorCode, String errorMessage) {
        return new SysException(errorCode, errorMessage);
    }

    public static SysException sysException(String errorMessage, Throwable e) {
        return new SysException(errorMessage, e);
    }

    public static SysException sysException(String errorCode, String errorMessage, Throwable e) {
        return new SysException(errorCode, errorMessage, e);
    }
    
    public static IntegrationException integrateException(String errorMessage) {
        return new IntegrationException(errorMessage);
    }

    public static IntegrationException integrateException(String errorCode, String errorMessage) {
        return new IntegrationException(errorCode, errorMessage);
    }

    
    public static IdempotentException idempotentException(String errorMessage) {
        return new IdempotentException(errorMessage);
    }

    public static IdempotentException idempotentException(String errorCode, String errorMessage) {
        return new IdempotentException(errorCode, errorMessage);
    }
}
