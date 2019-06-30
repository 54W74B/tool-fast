package com.intters.util.date;

import lombok.Getter;

import java.util.Calendar;

/**
 * 月份枚举
 * 与Calendar中的月份int值对应
 * @author Ruison
 * @date 2018/7/13.
 */
@Getter
public enum Month {

    /** 一月 */
    JANUARY(Calendar.JANUARY, "一月"),
    /** 二月 */
    FEBRUARY(Calendar.FEBRUARY, "二月"),
    /** 三月 */
    MARCH(Calendar.MARCH, "三月"),
    /** 四月 */
    APRIL(Calendar.APRIL, "四月"),
    /** 五月 */
    MAY(Calendar.MAY, "五月"),
    /** 六月 */
    JUNE(Calendar.JUNE, "六月"),
    /** 七月 */
    JULY(Calendar.JULY, "七月"),
    /** 八月 */
    AUGUST(Calendar.AUGUST, "八月"),
    /** 九月 */
    SEPTEMBER(Calendar.SEPTEMBER, "九月"),
    /** 十月 */
    OCTOBER(Calendar.OCTOBER, "十月"),
    /** 十一月 */
    NOVEMBER(Calendar.NOVEMBER, "十一月"),
    /** 十二月 */
    DECEMBER(Calendar.DECEMBER, "十二月"),
    /** 十三月，仅用于农历 */
    UNDECIMBER(Calendar.UNDECIMBER, "十三月"),
    ;

    private int num;
    private String value;

    Month(int num, String value) {
        this.num = num;
        this.value = value;
    }

    /**
     * 将 {@link Calendar}月份相关值转换为Month枚举对象
     * @see Calendar#JANUARY
     * @see Calendar#FEBRUARY
     * @see Calendar#MARCH
     * @see Calendar#APRIL
     * @see Calendar#MAY
     * @see Calendar#JUNE
     * @see Calendar#JULY
     * @see Calendar#AUGUST
     * @see Calendar#SEPTEMBER
     * @see Calendar#OCTOBER
     * @see Calendar#NOVEMBER
     * @see Calendar#DECEMBER
     * @see Calendar#UNDECIMBER
     * @param calendarMonthIntValue Calendar中关于Month的int值
     * @return {@link Month}
     */
    public static String toChinese(int calendarMonthIntValue) {
        switch (calendarMonthIntValue) {
            case Calendar.JANUARY:
                return JANUARY.getValue();
            case Calendar.FEBRUARY:
                return FEBRUARY.getValue();
            case Calendar.MARCH:
                return MARCH.getValue();
            case Calendar.APRIL:
                return APRIL.getValue();
            case Calendar.MAY:
                return MAY.getValue();
            case Calendar.JUNE:
                return JUNE.getValue();
            case Calendar.JULY:
                return JULY.getValue();
            case Calendar.AUGUST:
                return AUGUST.getValue();
            case Calendar.SEPTEMBER:
                return SEPTEMBER.getValue();
            case Calendar.OCTOBER:
                return OCTOBER.getValue();
            case Calendar.NOVEMBER:
                return NOVEMBER.getValue();
            case Calendar.DECEMBER:
                return DECEMBER.getValue();
            case Calendar.UNDECIMBER:
                return UNDECIMBER.getValue();
            default:
                return null;
        }
    }
}
