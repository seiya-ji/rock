/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.common.util;

import org.slf4j.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;


/**
 * <p>Title: DateUtil </p>
 * <p>Description: 日志工具类</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
public class LoggerUtil {
    /**
     * 构造函数
     */
    private LoggerUtil() {
    }

    /**
     * Info级别记录日志
     *
     * @param logger   slf4j logger实现
     * @param format   日志模版
     * @param argArray 日志参数
     */
    public static void info(Logger logger, String format, Object... argArray) {
        if (logger.isInfoEnabled()) {
            logger.info(format, argArray);
        }
    }

    /**
     * Info级别记录日志
     *
     * @param logger   slf4j logger实现
     * @param format   日志模版
     * @param e        异常
     * @param argArray 日志参数
     */
    public static void info(Logger logger, String format, Throwable e, Object... argArray) {
        if (logger.isInfoEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            logger.info(ft.getMessage(), e);
        }
    }

    /**
     * Warn级别记录日志
     *
     * @param logger   slf4j logger实现
     * @param format   日志模版
     * @param argArray 日志参数
     */
    public static void warn(Logger logger, String format, Object... argArray) {
        logger.warn(format, argArray);
    }

    /**
     * Warn级别记录日志
     *
     * @param logger   slf4j logger实现
     * @param format   日志模版
     * @param e        异常
     * @param argArray 日志参数
     */
    public static void warn(Logger logger, String format, Throwable e, Object... argArray) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        logger.warn(ft.getMessage(), e);
    }

    /**
     * Debug级别记录日志
     *
     * @param logger   slf4j logger实现
     * @param format   日志模版
     * @param argArray 日志参数
     */
    public static void debug(Logger logger, String format, Object... argArray) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, argArray);
        }
    }

    /**
     * Debug级别记录日志
     *
     * @param logger   slf4j logger实现
     * @param format   日志模版
     * @param e        异常
     * @param argArray 日志参数
     */
    public static void debug(Logger logger, String format, Throwable e, Object... argArray) {
        if (logger.isDebugEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            logger.debug(ft.getMessage(), e);
        }
    }

    /**
     * 错误级别记录日志
     *
     * @param logger   slf4j logger实现
     * @param format   日志模版
     * @param argArray 日志参数
     */
    public static void error(Logger logger, String format, Object... argArray) {
        logger.error(format, argArray);
    }

    /**
     * 错误级别记录日志
     *
     * @param logger   slf4j logger实现
     * @param format   日志模版
     * @param e        异常
     * @param argArray 日志参数
     */
    public static void error(Logger logger, String format, Throwable e, Object... argArray) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        logger.error(ft.getMessage(), e);
    }
}
