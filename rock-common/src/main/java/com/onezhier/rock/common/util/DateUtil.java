/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>Title: DateUtil </p>
 * <p>Description: 日期工具类</p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
@Slf4j
public class DateUtil {

    /**
     * seconds of day
     */
    public static final  long ONE_DAY_SECONDS = 86400;
    /**
     * mill seconds of day
     */
    public static final  long ONE_DAY_MILL_SECONDS = 86400000;

    /**
     * default datetime
     */
    public static final  String DEFAULT_DATE_TIME_STR = "1970-01-01 00:00:00";

    /**
     * yyyyMMdd日期格式
     */
    public static final  String SHORT_FORMAT = "yyyyMMdd";

    /**
     * yyyyMMddHHmmss日期格式
     */
    public static final  String LONG_FORMAT = "yyyyMMddHHmmss";

    /**
     * yyyyMMddHHmm 日期格式
     */
    public static final  String LONG_NOS_FORMAT = "yyyyMMddHHmm";

    /**
     * yyyy-MM-dd日期格式
     */
    public static final  String WEB_FORMAT = "yyyy-MM-dd";

    /**
     * HHmmss日期格式
     */
    public static final  String TIME_FORMAT = "HHmmss";

    /**
     * yyyyMM日期格式
     */
    public static final  String MONTH_FORMAT = "yyyyMM";

    /**
     * yyyy年MM月dd日 日期格式
     */
    public static final  String CHINESE_DT_FORMAT = "yyyy年MM月dd日";

    /**
     * yyyy-MM-dd HH:mm:ss日期格式
     */
    public static final  String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm日期格式
     */
    public static final  String NO_SECOND_FORMAT = "yyyy-MM-dd HH:mm";

   

    /**
     * @param pattern 日期字符
     * @return 日期格式
     */
    public static DateFormat getNewDateFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        df.setLenient(false);
        return df;
    }

    /**
     * 获取系统默认时间(计算机诞生时间)
     * @return 系统默认时间
     */
    public static Date defaultDate() {
        try {
            DateFormat dateFormat = new SimpleDateFormat(NEW_FORMAT);
            return dateFormat.parse(DEFAULT_DATE_TIME_STR);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param sDate  日期字符
     * @param format 日期格式
     * @return 日期
     * @throws ParseException 转换异常
     */
    public static Date parseDate(String sDate, String format) throws ParseException {
        if (StringUtils.isBlank(format)) {
            throw new ParseException("Null format. ", 0);
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        if ((sDate == null) || (sDate.length() < format.length())) {
            throw new ParseException("length too little", 0);
        }
        return dateFormat.parse(sDate);
    }

    /**
     * @param sDate 日期字符
     * @return 日期
     * @throws ParseException 转换异常
     */
    public static Date parseDateNoTimeFormat(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(SHORT_FORMAT);
        if ((sDate == null) || (sDate.length() < SHORT_FORMAT.length())) {
            throw new ParseException("length too little", 0);
        }
        if (!StringUtils.isNumeric(sDate)) {
            throw new ParseException("not all digit", 0);
        }
        return dateFormat.parse(sDate);
    }

    /**
     * @param sDate   日期字符
     * @param delimit 划界
     * @return 日期
     * @throws ParseException 转换异常
     */
    public static Date parseDateWithDelimit(String sDate, String delimit) throws ParseException {
        if (StringUtils.isEmpty(sDate)) {
            throw new ParseException("length not match", 0);
        }
        sDate = sDate.replaceAll(delimit, "");
        DateFormat dateFormat = new SimpleDateFormat(SHORT_FORMAT);
        if (sDate.length() != SHORT_FORMAT.length()) {
            throw new ParseException("length not match", 0);
        }
        return dateFormat.parse(sDate);
    }

    /**
     * @param sDate 日期字符
     * @return 日期
     */
    public static Date parseDateLongFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat(LONG_FORMAT);
        Date d = null;
        if ((sDate != null) && (sDate.length() == LONG_FORMAT.length())) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException ex) {
                return null;
            }
        }
        return d;
    }

    /**
     * @param sDate 日期字符
     * @return 日期
     */
    public static Date parseDateNewFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat(NEW_FORMAT);
        Date d = null;
        dateFormat.setLenient(false);
        if ((sDate != null) && (sDate.length() == NEW_FORMAT.length())) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException ex) {
                return null;
            }
        }
        return d;
    }

    /**
     * @param sDate 日期字符
     * @return 日期
     * @throws ParseException 转换异常
     */
    public static Date parseDateNoSecondFormat(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(NO_SECOND_FORMAT);

        if ((sDate != null) && (sDate.length() == NO_SECOND_FORMAT.length())) {
            return dateFormat.parse(sDate);
        }
        return null;
    }

    /**
     * 获得指定时间时间起点时间
     *
     * @param date 指定时间时间
     * @return 起点时间
     */
    public static Date getDayBegin(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setLenient(false);

        String dateString = df.format(date);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            return date;
        }
    }

    /**
     * 获取系统日期的前一天日期，返回Date
     *
     * @return 日期
     */
    public static Date getBeforeDate() {
        return new Date(new Date().getTime() - (ONE_DAY_MILL_SECONDS));
    }

    /**
     * 计算某时间几天之后的时间
     *
     * @param date 日期
     * @param days 累加天数
     * @return 累加后的日期
     */
    public static Date addDays(Date date, long days) {
        return addSeconds(date, days * ONE_DAY_SECONDS);
    }

    /**
     * 计算某时间几小时之后的时间
     *
     * @param date  日期
     * @param hours 累加小时
     * @return 累加后的日期
     */
    public static Date addHours(Date date, long hours) {
        return addMinutes(date, hours * 60);
    }

    /**
     * 计算某时间几分钟之后的时间
     *
     * @param date    日期
     * @param minutes 累加分钟
     * @return 累加后的日期
     */
    public static Date addMinutes(Date date, long minutes) {
        return addSeconds(date, minutes * 60);
    }

    /**
     * 计算某时间几秒之后的时间
     *
     * @param date 日期
     * @param secs 累加秒数
     * @return 累加后的日期
     */
    public static Date addSeconds(Date date, long secs) {
        return new Date(date.getTime() + (secs * 1000));
    }

    /**
     * 判断输入的字符串是否为合法的小时
     *
     * @param hourStr 小时字符
     * @return true/false
     */
    public static boolean isValidHour(String hourStr) {
        if (StringUtils.isNotEmpty(hourStr) && StringUtils.isNumeric(hourStr)) {
            int hour = new Integer(hourStr);
            return hour >= 0 && hour <= 23;
        }
        return false;
    }


    /**
     * @param date   日期
     * @param format 日期格式
     * @return 日期字符
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * @param sDate 日期字符
     * @return 日期字符
     * @throws ParseException 转换异常
     */
    public static String getTomorrowDateString(String sDate) throws ParseException {
        Date aDate = parseDateNoTimeFormat(sDate);
        aDate = addSeconds(aDate, ONE_DAY_SECONDS);
        return getDateString(aDate);
    }

    /**
     * @param date 日期
     * @return 日期字符
     */
    public static String getLongDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(LONG_FORMAT);
        return getDateString(date, dateFormat);
    }

    /**
     * @param date 日期
     * @return 日期字符
     */
    public static String getNewFormatDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(NEW_FORMAT);
        return getDateString(date, dateFormat);
    }

    /**
     * @param date       日期
     * @param dateFormat 日期格式
     * @return 日期字符
     */
    public static String getDateString(Date date, DateFormat dateFormat) {
        if (date == null || dateFormat == null) {
            return null;
        }
        return dateFormat.format(date);
    }

    /**
     * @param sDate 日期字符
     * @return 日期字符
     * @throws ParseException 转换异常
     */
    public static String getYesterdayDateString(String sDate) throws ParseException {
        Date aDate = parseDateNoTimeFormat(sDate);
        aDate = addSeconds(aDate, -ONE_DAY_SECONDS);
        return getDateString(aDate);
    }

    /**
     * @param date 日期
     * @return 格式化为"yyyyMMdd"的日期字符
     */
    public static String getDateString(Date date) {
        DateFormat df = getNewDateFormat(SHORT_FORMAT);
        return df.format(date);
    }

    /**
     * @param date 日期
     * @return 格式化为"yyyy-MM-dd的日期字符
     */
    public static String getWebDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat(WEB_FORMAT);
        return getDateString(date, dateFormat);
    }

    /**
     * 取得“X年X月X日”的日期格式
     *
     * @param date 日期
     * @return “X年X月X日”的日期格式
     */
    public static String getChineseDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat(CHINESE_DT_FORMAT);
        return getDateString(date, dateFormat);
    }

    /**
     * @return 当天"yyyyMMdd"日期格式
     */
    public static String getTodayString() {
        DateFormat dateFormat = getNewDateFormat(SHORT_FORMAT);
        return getDateString(new Date(), dateFormat);
    }

    /**
     * @param date 日期
     * @return 日期字符
     */
    public static String getTimeString(Date date) {
        DateFormat dateFormat = getNewDateFormat(TIME_FORMAT);
        return getDateString(date, dateFormat);
    }

    /**
     * @param days 间隔天数
     * @return 相隔天数的日期字符
     */
    public static String getBeforeDayString(int days) {
        Date date = new Date(System.currentTimeMillis() - (ONE_DAY_MILL_SECONDS * days));
        DateFormat dateFormat = getNewDateFormat(SHORT_FORMAT);
        return getDateString(date, dateFormat);
    }

    /**
     * @param dateString 日期字符
     * @param days       间隔天数
     * @return 间隔天数后的日期字符
     */
    public static String getBeforeDayString(String dateString, int days) {
        Date date;
        DateFormat df = getNewDateFormat(SHORT_FORMAT);
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            date = new Date();
        }
        date = new Date(date.getTime() - (ONE_DAY_MILL_SECONDS * days));
        return df.format(date);
    }

    /**
     * @param strDate 日期字符
     * @return 日期字符
     */
    public static String getShortDateString(String strDate) {
        return getShortDateString(strDate, "-|/");
    }


    /**
     * 取得两个日期间隔秒数（日期1-日期2）
     *
     * @param one 日期1
     * @param two 日期2
     * @return 间隔秒数
     */
    public static long getDiffSeconds(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
    }

    /**
     * @param one 日期1
     * @param two 日期2
     * @return 间隔分钟数
     */
    public static long getDiffMinutes(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 1000);
    }

    /**
     * 取得两个日期的间隔天数
     *
     * @param one 日期1
     * @param two 日期2
     * @return 间隔天数
     */
    public static long getDiffDays(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
    }


    /**
     * @param strDate   日期字符
     * @param delimiter 间隔符
     * @return 日期字符
     */
    public static String getShortDateString(String strDate, String delimiter) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        String temp = strDate.replaceAll(delimiter, "");
        if (isValidShortDateFormat(temp)) {
            return temp;
        }
        return null;
    }

    /**
     * @return 获取当月第一天的段日期字符
     */
    public static String getShortFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        Date dt = new Date();
        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        DateFormat df = getNewDateFormat(SHORT_FORMAT);
        return df.format(cal.getTime());
    }

    /**
     * @return 日期字符
     */
    public static String getWebTodayString() {
        DateFormat df = getNewDateFormat(WEB_FORMAT);
        return df.format(new Date());
    }

    /**
     * @return 日期字符
     */
    public static String getWebFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        Date dt = new Date();
        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        DateFormat df = getNewDateFormat(WEB_FORMAT);
        return df.format(cal.getTime());
    }

    /**
     * @param dateString 日期字符
     * @param formatIn   输入日期字符
     * @param formatOut  输出日期字符
     * @return 日期字符
     */
    public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
        try {
            Date date = formatIn.parse(dateString);
            return formatOut.format(date);
        } catch (ParseException e) {
            log.warn("convert() --- origin date error: " + dateString);
            return "";
        }
    }

    /**
     * @param dateString 日期字符
     * @return 日期字符
     */
    public static String convert2WebFormat(String dateString) {
        DateFormat df1 = getNewDateFormat(SHORT_FORMAT);
        DateFormat df2 = getNewDateFormat(WEB_FORMAT);
        return convert(dateString, df1, df2);
    }

    /**
     * @param dateString 日期字符
     * @return 日期字符
     */
    public static String convert2ChineseDtFormat(String dateString) {
        DateFormat df1 = getNewDateFormat(SHORT_FORMAT);
        DateFormat df2 = getNewDateFormat(CHINESE_DT_FORMAT);
        return convert(dateString, df1, df2);
    }

    /**
     * @param dateString 日期字符
     * @return 日期字符
     */
    public static String convertFromWebFormat(String dateString) {
        DateFormat df1 = getNewDateFormat(SHORT_FORMAT);
        DateFormat df2 = getNewDateFormat(WEB_FORMAT);
        return convert(dateString, df2, df1);
    }

    /**
     * @param date 日期
     * @return 日期字符
     */
    public static String getEmailDate(Date date) {
        String todayStr;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        todayStr = sdf.format(date);
        return todayStr;
    }

    /**
     * @param date date
     * @return 日期字符
     */
    public static String getSmsDate(Date date) {
        String todayStr;
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");
        todayStr = sdf.format(date);
        return todayStr;
    }

    /**
     * @param date 日期
     * @return 日期格式
     */
    public static String formatMonth(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(MONTH_FORMAT).format(date);
    }

    /**
     * 判断输入的字符串是否为合法的分或秒
     *
     * @param str 分或秒字符
     * @return true/false
     */
    public static boolean isValidMinuteOrSecond(String str) {
        if (!StringUtils.isEmpty(str) && StringUtils.isNumeric(str)) {
            int hour = new Integer(str);
            return hour >= 0 && hour <= 59;
        }
        return false;
    }

    /**
     * 是否有效日期格式
     *
     * @param strDate 日期字符
     * @return true/false
     */
    public static boolean isValidShortDateFormat(String strDate) {
        if (strDate.length() != SHORT_FORMAT.length()) {
            return false;
        }

        try {
            Integer.parseInt(strDate); //---- 避免日期中输入非数字 ----
        } catch (Exception NumberFormatException) {
            return false;
        }

        DateFormat df = getNewDateFormat(SHORT_FORMAT);
        try {
            df.parse(strDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 是否有效日期格式
     *
     * @param strDate   日期字符
     * @param delimiter 间隔符
     * @return true/false
     */
    public static boolean isValidShortDateFormat(String strDate, String delimiter) {
        String temp = strDate.replaceAll(delimiter, "");
        return isValidShortDateFormat(temp);
    }

    /**
     * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
     *
     * @param strDate 日期字符
     * @return true/false
     */
    public static boolean isValidLongDateFormat(String strDate) {
        if (strDate.length() != LONG_FORMAT.length()) {
            return false;
        }

        try {
            Long.parseLong(strDate); //---- 避免日期中输入非数字 ----
        } catch (Exception NumberFormatException) {
            return false;
        }

        DateFormat df = getNewDateFormat(LONG_FORMAT);
        try {
            df.parse(strDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
     *
     * @param strDate   日期字符
     * @param delimiter 间隔符
     * @return true/false
     */
    public static boolean isValidLongDateFormat(String strDate, String delimiter) {
        String temp = strDate.replaceAll(delimiter, "");
        return isValidLongDateFormat(temp);
    }

    /**
     * @param date1 日期字符
     * @param date2 日期字符
     * @return true/false
     */
    public static boolean webDateNotLessThan(String date1, String date2) {
        DateFormat df = getNewDateFormat(WEB_FORMAT);
        return dateNotLessThan(date1, date2, df);
    }

    /**
     * @param date1  日期字符
     * @param date2  日期字符
     * @param format 日期格式
     * @return true/false
     */
    public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
        try {
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);

            return !d1.before(d2);
        } catch (ParseException e) {
            log.debug("dateNotLessThan() --- ParseException(" + date1 + ", " + date2 + ")");
            return false;
        }
    }

    /**
     * @param date 日期
     * @return true/false
     */
    public static boolean isBeforeNow(Date date) {
        if (date == null) {
            return false;
        }
        return date.compareTo(new Date()) < 0;
    }
    
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
      }

      /**
       * 根据传入时间格式，转换为 sql timesamp.
       *
       * @param timeString 只接受 yyyy-MM-dd HH:mm:ss 格式
       */
      public static Timestamp getTimestamp(String timeString) throws ParseException {
        if (StringUtils.isEmpty(timeString)) {
          return null;
        }
        return new Timestamp(new SimpleDateFormat(NEW_FORMAT).parse(timeString).getTime());
      }

      public static String getDateStringFromTimeStamp(Timestamp timestamp) {
        return new SimpleDateFormat(NEW_FORMAT).format(timestamp);
      }
}
