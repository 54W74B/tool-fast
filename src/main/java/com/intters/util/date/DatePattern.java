package com.intters.util.date;

/**
 * @author Ruison
 * @date 2018/7/13.
 */
public class DatePattern {

    /** 1分钟 */
    public final static long MINUTE = 60 * 1000;
    /** 1小时 */
    public final static long HOUR = 60 * MINUTE;
    /** 1天 */
    public final static long DAY = 24 * HOUR;
    /** 月 */
    public final static long MONTH = 31 * DAY;
    /** 年 */
    public final static long YEAR = 12 * MONTH;

    //-------------------------------------------------------------------------------------------------------------------------------- Normal
    /** 标准日期格式：yyyy-MM-dd */
    public final static String NORM_DATE_PATTERN = "yyyy-MM-dd";
    /** 标准日期格式：yyyy/MM/dd */
    public final static String NORM_DATE_PATTERN_SPE = "yyyy/MM/dd";
    /** 标准时间格式：MM/dd */
    public final static String NORM_DAY_PATTERN_SPE = "MM/dd";
    /** 标准时间格式：HH:mm */
    public final static String NORM_TIME_PATTERN = "HH:mm";
    /** 标准时间格式：HH:mm:ss */
    public final static String NORM_TIME_MINUTE_PATTERN = "HH:mm:ss";
    /** 标准日期时间格式，精确到分：yyyy-MM-dd HH:mm */
    public final static String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    /** 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss */
    public final static String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /** 标准日期时间格式，精确到秒：yyyy/MM/dd HH:mm:ss */
    public final static String NORM_DATETIME_PATTERN_SPE = "yyyy/MM/dd HH:mm:ss";
    /** 标准日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS */
    public final static String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    /** 标准日期时间格式，精确到毫秒：yyyy/MM/dd HH:mm:ss.SSS */
    public final static String NORM_DATETIME_MS_PATTERN_SPE = "yyyy/MM/dd HH:mm:ss.SSS";

    //-------------------------------------------------------------------------------------------------------------------------------- Pure
    /** 标准日期格式：yyyyMMdd */
    public final static String PURE_DATE_PATTERN = "yyyyMMdd";
    /** 标准日期格式：HHmmss */
    public final static String PURE_TIME_PATTERN = "HHmmss";
    /** 标准日期格式：yyyyMMddHHmmss */
    public final static String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";
    /** 标准日期格式：yyyyMMddHHmmssSSS */
    public final static String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";

    //-------------------------------------------------------------------------------------------------------------------------------- Chinese
    /** 中文日期格式：yyyy年MM月dd日 HH:mm */
    public final static String CHINESE_DATE_TIME = "yyyy年MM月dd日 HH:mm";
    /** 中文日期格式：yyyy年MM月dd日 HH:mm:ss */
    public final static String CHINESE_DATE_TIME_MINUTE = "yyyy年MM月dd日 HH:mm:ss";
    /** 中文日期格式：yyyy年MM月dd日 */
    public final static String CHINESE_YEAR_DATE = "yyyy年MM月dd日";
    /** 中文日期格式：MM月dd日 */
    public final static String CHINESE_DATE = "MM月dd日";
}
