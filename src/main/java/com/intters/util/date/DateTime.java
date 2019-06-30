package com.intters.util.date;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Ruison
 * @date 2018/7/13.
 */
public class DateTime extends Date {

    /** 是否可变对象 */
    private boolean mutable = true;
    /** 一周的第一天，默认是周一， 在设置或获得 WEEK_OF_MONTH 或 WEEK_OF_YEAR 字段时，Calendar 必须确定一个月或一年的第一个星期，以此作为参考点。 */
    private Week firstDayOfWeek = Week.MONDAY;

    /**
     * 转换JDK date为 DateTime
     * @param date JDK Date
     * @return DateTime
     */
    public static DateTime of(Date date) {
        if(date instanceof DateTime) {
            return (DateTime)date;
        }
        return new DateTime(date);
    }

    /**
     * 创建Date，日期默认为今日
     * @return DateTime
     */
    public static DateTime create() {
        return new DateTime(new DateTime());
    }

    // -------------------------------------------------------------------- Constructor start

    public DateTime() {
        super();
    }

    /**
     * 给定日期的构造
     * @param date 日期
     */
    public DateTime(Date date) {
        this(date.getTime());
    }

    /**
     * 给定日期的构造
     * @param calendar {@link Calendar}
     */
    public DateTime(Calendar calendar) {
        this(calendar.getTime());
    }

    /**
     * 给定日期毫秒数的构造
     * @param timeMillis 日期毫秒数
     */
    public DateTime(long timeMillis) {
        super(timeMillis);
    }

    /**
     * 构造
     * @see DatePattern
     * @param dateStr Date字符串
     * @param format 格式
     */
    public DateTime(String dateStr, String format) {
        this(dateStr, new SimpleDateFormat(format));
    }

    /**
     * 构造
     * @see DatePattern
     * @param dateStr Date字符串
     * @param dateFormat 格式化器 {@link SimpleDateFormat}
     */
    public DateTime(String dateStr, DateFormat dateFormat) {
        this(parse(dateStr, dateFormat));
    }
    // -------------------------------------------------------------------- Constructor end

    /**
     * 今日格式化输出
     * @param format 日期类型
     * @return
     */
    public String fromDate(String format) {
        return format(new SimpleDateFormat(format));
    }

    /**
     * 转换 {@link Calendar} 为 DateTime
     * @param calendar {@link Calendar}
     * @return DateTime
     */
    public static DateTime of(Calendar calendar) {
        return new DateTime(calendar);
    }

    /**
     * 构造
     * @see DatePattern
     * @param dateStr Date字符串
     * @param format 格式
     * @return {@link DateTime}
     */
    public static DateTime of(String dateStr, String format) {
        return new DateTime(dateStr, format);
    }

    /**
     * 转换字符串为Date
     * @param dateStr 日期字符串
     * @param dateFormat {@link SimpleDateFormat}
     * @return {@link Date}
     */
    private static Date parse(String dateStr, DateFormat dateFormat) {
        try {
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            String pattern;
            if (dateFormat instanceof SimpleDateFormat) {
                pattern = ((SimpleDateFormat) dateFormat).toPattern();
            } else {
                pattern = dateFormat.toString();
            }
            throw new DateException(String.format("Parse [%s] with format [%s] error!", dateStr, pattern), e);
        }
    }

    private String format(DateFormat dateFormat) {
        return dateFormat.format(this);
    }

    /**
     * 调整日期和时间<br>
     * 如果此对象为可变对象，返回自身，否则返回新对象，设置是否可变对象见
     * @param datePart 调整的部分 {@link DateField}
     * @param offset 偏移量，正数为向后偏移，负数为向前偏移
     * @return 如果此对象为可变对象，返回自身，否则返回新对象
     */
    public DateTime offset(DateField datePart, int offset) {
        final Calendar cal = toCalendar();
        cal.add(datePart.getValue(), offset);
        DateTime dt = this;
        return dt.setTimeInternal(cal.getTimeInMillis());
    }

    /**
     * 获得日期的某个部分
     * 例如获得年的部分，则使用 getField(DatePart.YEAR)
     * @param field 表示日期的哪个部分的枚举 {@link DateField}
     * @return 某个部分的值
     */
    public int getField(DateField field) {
        return getField(field.getValue());
    }

    /**
     * 获得日期的某个部分
     * 例如获得年的部分，则使用 getField(Calendar.YEAR)
     * @param field 表示日期的哪个部分的int值 {@link Calendar}
     * @return 某个部分的值
     */
    public int getField(int field) {
        return toCalendar().get(field);
    }

    /**
     * 获得年的部分
     * @return 年的部分
     */
    public int year() {
        return getField(DateField.YEAR);
    }

    /**
     * 获得年的天数
     * @return
     */
    public int yearNum() {
        return toCalendar().getActualMaximum(DateField.DAY_OF_YEAR.getValue());
    }

    /**
     * 获得年剩下的天数
     * @return
     */
    public int restOfYear() {
        return getField(DateField.DAY_OF_YEAR);
    }

    /**
     * 获得月份
     * @return 月份
     */
    public int month() {
        return getField(DateField.MONTH);
    }

    /**
     * 获得月份，从1开始计数<br>
     * 由于{@link Calendar} 中的月份按照0开始计数，导致某些需求容易误解，因此如果想用1表示一月，2表示二月则调用此方法
     *
     * @return 月份
     */
    public int monthStartFromOne() {
        return month() + 1;
    }

    /**
     * 中文格式月份
     * @return
     */
    public String monthToChinese() {
        return Month.toChinese(month());
    }

    /**
     * 获得当前日期所属季度，从1开始计数<br>
     * @return 第几个季度 {@link Quarter}
     */
    public int quarter() {
        return month() / 3 + 1;
    }

    /**
     * 获得当前日期所属季度<br>
     * @return 第几个季度 {@link Quarter}
     */
    public Quarter quarterEnum() {
        return Quarter.of(quarter());
    }

    /**
     * 获取当前日期所属季度中文格式
     * @return 中文格式季度
     */
    public String quarterToChinese() {
        return Quarter.toChinese(quarter());
    }

    /**
     * 获得指定日期是所在年份的第几周<br>
     * 此方法返回值与一周的第一天有关，比如：<br>
     * 2016年1月3日为周日，如果一周的第一天为周日，那这天是第二周（返回2）<br>
     * 如果一周的第一天为周一，那这天是第一周（返回1）
     *
     * @return 周
     * @see #setFirstDayOfWeek(Week)
     */
    public int weekOfYear() {
        return getField(DateField.WEEK_OF_YEAR);
    }

    /**
     * 数字周几
     * @return
     */
    public int week() {
        return getField(DateField.DAY_OF_WEEK);
    }

    /**
     * 今天中文日期名
     * @param weekNamePre 表示星期的前缀，例如前缀为“星期”，则返回结果为“星期一”；前缀为”周“，结果为“周一”
     * @return 中文日期名
     */
    public String weekToChina(String weekNamePre) {
        if (StringUtils.isNotBlank(weekNamePre)) {
            return Week.of(week()).toChinese(weekNamePre);
        }
        return Week.of(week()).toChinese();
    }

    /**
     * 这个月有多少天
     * @return 某个部分的值
     */
    public int monthNum() {
        return toCalendar().getActualMaximum(DateField.DAY_OF_MONTH.getValue());
    }

    public int monthWeek() {
        return toCalendar().getActualMaximum(DateField.WEEK_OF_MONTH.getValue());
    }



    /**
     * 是否闰年
     *
     * @param year 年
     * @return 是否闰年
     */
    public static boolean isLeapYear(int year) {
        return new GregorianCalendar().isLeapYear(year);
    }

    /**
     * 转换为Calendar，默认{@link TimeZone}，默认 {@link Locale}
     * @return {@link Calendar}
     */
    public Calendar toCalendar() {
        final Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(firstDayOfWeek.getValue());
        cal.setTime(this);
        return cal;
    }

    /**
     * 设置一周的第一天<br>
     * JDK的Calendar中默认一周的第一天是周日，tool中将此默认值设置为周一<br>
     * 设置一周的第一天主要影响{@link #weekOfMonth()}和{@link #weekOfYear()} 两个方法
     *
     * @param firstDayOfWeek 一周的第一天
     * @return this
     * @see #weekOfMonth()
     * @see #weekOfYear()
     */
    public DateTime setFirstDayOfWeek(Week firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
        return this;
    }

    /**
     * 获得指定日期是所在月份的第几周<br>
     * 此方法返回值与一周的第一天有关，比如：<br>
     * 2016年1月3日为周日，如果一周的第一天为周日，那这天是第二周（返回2）<br>
     * 如果一周的第一天为周一，那这天是第一周（返回1）
     * @return 周
     * @see #setFirstDayOfWeek(Week)
     */
    public int weekOfMonth() {
        return getField(DateField.WEEK_OF_MONTH);
    }

    /**
     * 设置日期时间
     * @param time 日期时间毫秒
     * @return this
     */
    private DateTime setTimeInternal(long time) {
        super.setTime(time);
        return this;
    }
}
