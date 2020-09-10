package com.intters.util.date;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 日期工具类扩展
 *
 * @author Ruison
 * @date 2020/9/10-11:47
 */
public class DateKtUtil extends DateUtil {

    /**
     * 1分钟
     */
    public final static long MINUTE = 60 * 1000;
    /**
     * 1小时
     */
    public final static long HOUR = 60 * MINUTE;
    /**
     * 1天
     */
    public final static long DAY = 24 * HOUR;
    /**
     * 月
     */
    public final static long MONTH = 31 * DAY;
    /**
     * 年
     */
    public final static long YEAR = 12 * MONTH;

    /**
     * date转化为文字类型的时间描述
     *
     * @param date 日期
     * @return 文本时间描述
     */
    public static String timeToText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = System.currentTimeMillis() - date.getTime();
        long r = 0;
        if (diff > YEAR) {
            r = (diff / YEAR);
            return r + "年前";
        }
        if (diff > MONTH) {
            r = (diff / MONTH);
            return r + "个月前";
        }
        if (diff > DAY) {
            r = (diff / DAY);
            return r + "天前";
        }
        if (diff > HOUR) {
            r = (diff / HOUR);
            return r + "个小时前";
        }
        if (diff > MINUTE) {
            r = (diff / MINUTE);
            return r + "分钟前";
        }
        return "刚刚";
    }
}
