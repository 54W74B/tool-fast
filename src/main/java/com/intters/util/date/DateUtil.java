package com.intters.util.date;

import com.intters.util.NumberUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author Ruison
 * @date 2018/7/12.
 */
public class DateUtil {

    /**
     * 设置日期
     * @param date
     * @return
     */
    public static DateTime set(Date date) {
        return DateTime.of(date);
    }

    /**
     * 当天日期 yyyy-MM-dd
     * @return 日期字符串
     */
    public static String nowDateFormatStr() {
        return DateTime.create().fromDate(DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 当天日期 yyyy/MM/dd
     * @return 日期字符串
     */
    public static String nowDateFormatStrSpe() {
        return DateTime.create().fromDate(DatePattern.NORM_DATE_PATTERN_SPE);
    }

    /**
     * 当天日期 MM/dd
     * @return 日期字符串
     */
    public static String nowDayFormatStrSpe() {
        return DateTime.create().fromDate(DatePattern.NORM_DAY_PATTERN_SPE);
    }

    /**
     * 当天日期 HH:mm
     * @return 日期字符串
     */
    public static String nowTimeFormatStr() {
        return DateTime.create().fromDate(DatePattern.NORM_TIME_PATTERN);
    }

    /**
     * 当天日期 HH:mm:ss
     * @return 日期字符串
     */
    public static String nowTimeMinuteFormatStr() {
        return DateTime.create().fromDate(DatePattern.NORM_TIME_MINUTE_PATTERN);
    }

    /**
     * 当天日期 yyyy-MM-dd HH:mm
     * @return 日期字符串
     */
    public static String nowDateTimeMimuteFormatStr() {
        return DateTime.create().fromDate(DatePattern.NORM_DATETIME_MINUTE_PATTERN);
    }

    /**
     * 当天日期 yyyy-MM-dd HH:mm:ss
     * @return 日期字符串
     */
    public static String nowDateTimeFormatStr() {
        return DateTime.create().fromDate(DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 当天日期 yyyy/MM/dd HH:mm:ss
     * @return 日期字符串
     */
    public static String nowDateTimeFormatStrSpe() {
        return DateTime.create().fromDate(DatePattern.NORM_DATETIME_PATTERN_SPE);
    }

    /**
     * 当天日期 yyyy-MM-dd HH:mm:ss.SSS
     * @return 日期字符串
     */
    public static String nowDateTimeMsFormatStr() {
        return DateTime.of(new Date()).fromDate(DatePattern.NORM_DATETIME_MS_PATTERN);
    }

    /**
     * 当天日期 yyyy/MM/dd HH:mm:ss.SSS
     * @return 日期字符串
     */
    public static String nowDateTimeMsFormatStrSpe() {
        return DateTime.create().fromDate(DatePattern.NORM_DATETIME_MS_PATTERN_SPE);
    }

    /**
     * 当天日期 yyyy年MM月dd日 HH:mm
     * @return 日期字符串
     */
    public static String nowDateTimeFormatChinese() {
        return DateTime.create().fromDate(DatePattern.CHINESE_DATE_TIME);
    }

    /**
     * 当天日期 yyyy年MM月dd日 HH:mm:ss
     * @return 日期字符串
     */
    public static String nowDateTimeMiuteFormatChinese() {
        return DateTime.create().fromDate(DatePattern.CHINESE_DATE_TIME_MINUTE);
    }

    /**
     * 当天日期 yyyy年MM月dd日
     * @return 日期字符串
     */
    public static String nowYearDateFormatChinese() {
        return DateTime.create().fromDate(DatePattern.CHINESE_YEAR_DATE);
    }

    /**
     * 当天日期 MM月dd日
     * @return 日期字符串
     */
    public static String nowDateFormatChinese() {
        return DateTime.create().fromDate(DatePattern.CHINESE_DATE);
    }

    /**
     * date转化为文字类型的时间描述
     * @param date
     * @return
     */
    public static String timeToText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = System.currentTimeMillis() - date.getTime();
        long r = 0;
        if (diff > DatePattern.YEAR) {
            r = (diff / DatePattern.YEAR);
            return r + "年前";
        }
        if (diff > DatePattern.MONTH) {
            r = (diff / DatePattern.MONTH);
            return r + "个月前";
        }
        if (diff > DatePattern.DAY) {
            r = (diff / DatePattern.DAY);
            return r + "天前";
        }
        if (diff > DatePattern.HOUR) {
            r = (diff / DatePattern.HOUR);
            return r + "个小时前";
        }
        if (diff > DatePattern.MINUTE) {
            r = (diff / DatePattern.MINUTE);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 自动识别日期类型
     * <ol>
     * <li>yyyy-MM-dd HH:mm:ss</li>
     * <li>yyyy/MM/dd HH:mm:ss</li>
     * <li>yyyy.MM.dd HH:mm:ss</li>
     * <li>yyyy年MM月dd日 HH时mm分ss秒</li>
     * <li>yyyy-MM-dd</li>
     * <li>yyyy/MM/dd</li>
     * <li>yyyy.MM.dd</li>
     * <li>HH:mm:ss</li>
     * <li>HH时mm分ss秒</li>
     * <li>yyyy-MM-dd HH:mm</li>
     * <li>yyyy-MM-dd HH:mm:ss.SSS</li>
     * <li>yyyyMMddHHmmss</li>
     * <li>yyyyMMddHHmmssSSS</li>
     * <li>yyyyMMdd</li>
     * </ol>
     * @param dateStr 日期字符串
     * @return 日期
     * @throws ParseException
     */
    public static DateTime parse(String dateStr) throws ParseException {
        if (null == dateStr) {
            return null;
        }
        // 去掉两边空格并去掉中文日期中的“日”，以规范长度
        dateStr = dateStr.trim().replace("日", "");
        int length = dateStr.length();

        if (NumberUtil.isNumber(dateStr)) {
            // 纯数字形式
            if (length == DatePattern.PURE_DATETIME_PATTERN.length()) {
                return DateTime.of(dateStr, DatePattern.PURE_DATETIME_PATTERN);
            } else if (length == DatePattern.PURE_DATETIME_MS_PATTERN.length()) {
                return DateTime.of(dateStr, DatePattern.PURE_DATETIME_MS_PATTERN);
            } else if (length == DatePattern.PURE_DATE_PATTERN.length()) {
                return DateTime.of(dateStr, DatePattern.PURE_DATE_PATTERN);
            } else if (length == DatePattern.PURE_TIME_PATTERN.length()) {
                return DateTime.of(dateStr, DatePattern.PURE_TIME_PATTERN);
            }
        }

        if (length == DatePattern.NORM_DATETIME_PATTERN.length() || length == DatePattern.NORM_DATETIME_PATTERN.length() + 1) {
            return DateTime.of(dateStr, DatePattern.NORM_DATETIME_PATTERN);
        } else if (length == DatePattern.NORM_DATE_PATTERN.length()) {
            return DateTime.of(dateStr, DatePattern.NORM_DATE_PATTERN);
        } else if (length == DatePattern.NORM_TIME_MINUTE_PATTERN.length() || length == DatePattern.NORM_TIME_MINUTE_PATTERN.length() + 1) {
            return DateTime.of(dateStr, DatePattern.NORM_TIME_MINUTE_PATTERN);
        } else if (length == DatePattern.NORM_DATETIME_MINUTE_PATTERN.length() || length == DatePattern.NORM_DATETIME_MINUTE_PATTERN.length() + 1) {
            return DateTime.of(dateStr, DatePattern.NORM_DATETIME_MINUTE_PATTERN);
        } else if (length >= DatePattern.NORM_DATETIME_MS_PATTERN.length() - 2) {
            return DateTime.of(dateStr, DatePattern.NORM_DATETIME_MS_PATTERN);
        } else {
            return null;
        }
    }

    /**
     * 根据日期类型转化为string
     * @param date 日期
     * @param format 日期格式
     * @return 字符串
     */
    public static String dateToStr(Date date, String format) {
        return DateTime.of(date).fromDate(format);
    }

    /**
     * 今天日期date转中文日期名
     * @param weekNamePre 表示星期的前缀，例如前缀为“星期”，则返回结果为“星期一”；前缀为”周“，结果为“周一”
     * @return 中文日期名
     */
    public static String nowDateToChinese(String weekNamePre) {
        return DateTime.create().weekToChina(weekNamePre);
    }

    /**
     * 某一天
     *
     * @param offset 天数偏移量
     * @return date格式日期
     */
    public static Date someDay(int offset) {
        return DateTime.create().offset(DateField.DATE, offset);
    }

    /**
     * 某一天
     * @param offset 天数偏移量
     * @param weekNamePre weekNamePre 表示星期的前缀，例如前缀为“星期”，则返回结果为“星期一”；前缀为”周“，结果为“周一”
     * @return 中文日期名
     */
    public static String someDayToChinese(int offset, String weekNamePre) {
        return DateTime.create().offset(DateField.DAY_OF_WEEK, offset).weekToChina(weekNamePre);
    }

    /**
     * 某一天
     *
     * @param offset 天数偏移量
     * @param format 日期格式
     * @return 字符串
     */
    public static String someDayFormat(int offset, String format) {
        return DateTime.create().offset(DateField.DATE, offset).fromDate(format);
    }

    /**
     * 今年
     * @return 数字格式年份
     */
    public static int year() {
        return DateTime.create().year();
    }

    /**
     * 某一年
     * @param offset 年的偏移量
     * @return 数字格式年份
     */
    public static int someYear(int offset) {
        return DateTime.of(new DateTime()).offset(DateField.YEAR, offset).year();
    }

    /**
     * 今年的天数
     * @return
     */
    public static int yearNum() {
        return DateTime.create().yearNum();
    }

    /**
     * 某一年的天数
     * @param offset 偏移量
     * @return
     */
    public static int someYearNum(int offset) {
        return DateTime.of(new DateTime()).offset(DateField.YEAR, offset).yearNum();
    }

    /**
     * 今年剩下的天数
     * @return
     */
    public static int restOfYear() {
        return DateTime.create().restOfYear();
    }

    /**
     * 本月
     * @return
     */
    public static int month() {
        return DateTime.create().monthStartFromOne();
    }

    /**
     * 本月中文格式
     * @return
     */
    public static String monthToChina() {
        return DateTime.create().monthToChinese();
    }

    /**
     * 本周是今年的第几周
     * @return
     */
    public static int weekOfYear() {
        return DateTime.create().weekOfYear();
    }

    /**
     * 本周在本月是第几周
     * @return
     */
    public static int weekOfMonth() {
        return DateTime.create().weekOfMonth();
    }

    /**
     * 当前是第几季度
     * @return 数字格式季度
     */
    public static int quarter() {
        return DateTime.create().quarter();
    }

    /**
     * 当前是第几季度 中文格式
     * @return 中文格式季度
     */
    public static String quarterToChina() {
        return DateTime.create().quarterToChinese();
    }

    /**
     * 偏移天
     * @param date 日期
     * @param offset 偏移天数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static DateTime offsetDay(Date date, int offset) {
        return offset(date, DateField.DAY_OF_YEAR, offset);
    }

    /**
     * 偏移天
     * @param offset 偏移天数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static DateTime offsetDay(int offset) {
        return offset(new DateTime(), DateField.DAY_OF_YEAR, offset);
    }

    /**
     * 偏移月
     * @param date 日期
     * @param offset 偏移月数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static DateTime offsetMonth(Date date, int offset) {
        return offset(date, DateField.MONTH, offset);
    }

    /**
     * 偏移月
     * @param offset 偏移月数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static DateTime offsetMonth(int offset) {
        return offset(new DateTime(), DateField.MONTH, offset);
    }

    /**
     * 获取指定日期偏移指定时间后的时间
     *
     * @param date 基准日期
     * @param dateField 偏移的粒度大小（小时、天、月等）{@link DateField}
     * @param offset 偏移量，正数为向后偏移，负数为向前偏移
     * @return 偏移后的日期
     */
    public static DateTime offset(Date date, DateField dateField, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(dateField.getValue(), offset);
        return new DateTime(cal.getTime());
    }

    /**
     * 转换为{@link DateTime}对象
     * @return 当前时间
     */
    public static DateTime date() {
        return new DateTime();
    }

    /**
     * 本月第一天
     * @return
     */
    public static DateTime firstDate() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        ca.set(Calendar.DAY_OF_MONTH,1);
        //将小时至0
        ca.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        ca.set(Calendar.MINUTE, 0);
        //将秒至0
        ca.set(Calendar.SECOND,0);
        //将毫秒至0
        ca.set(Calendar.MILLISECOND, 0);
        return DateTime.of(ca);
    }

    /**
     * 某月第一天
     * @return
     */
    public static DateTime firstDate(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        ca.set(Calendar.DAY_OF_MONTH,1);
        //将小时至0
        ca.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        ca.set(Calendar.MINUTE, 0);
        //将秒至0
        ca.set(Calendar.SECOND,0);
        //将毫秒至0
        ca.set(Calendar.MILLISECOND, 0);
        return DateTime.of(ca);
    }

    /**
     * 本月最后一天
     * @return
     */
    public static DateTime lastDate() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至0
        ca.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        ca.set(Calendar.MINUTE, 0);
        //将秒至0
        ca.set(Calendar.SECOND,0);
        //将毫秒至0
        ca.set(Calendar.MILLISECOND, 0);
        //在当前月的下一月基础上减去1毫秒
        ca.add(Calendar.MILLISECOND, -1);
        return DateTime.of(ca);
    }

    /**
     * 某月最后一天
     * @return
     */
    public static DateTime lastDate(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        ca.setTime(date);
        //将小时至0
        ca.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        ca.set(Calendar.MINUTE, 0);
        //将秒至0
        ca.set(Calendar.SECOND,0);
        //将毫秒至0
        ca.set(Calendar.MILLISECOND, 0);
        //在当前月的下一月基础上减去1毫秒
        ca.add(Calendar.MILLISECOND, -1);
        return DateTime.of(ca);
    }

    /**
     * 本月多少天
     * @return
     */
    public static int monthNum() {
        return DateTime.create().monthNum();
    }

    /**
     * 本月有多少周
     * @return
     */
    public static int monthWeek() {
        return DateTime.create().monthWeek();
    }

    /**
     * 计算相对于dateToCompare的年龄，长用于计算指定生日在某年的年龄
     *
     * @param birthDay 生日
     * @param dateToCompare 需要对比的日期
     * @return 年龄
     */
    public static int age(Date birthDay, Date dateToCompare) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToCompare);

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(String.format("Birthday is after date {%s}!", dateToStr(dateToCompare, DatePattern.CHINESE_DATE_TIME_MINUTE)));
        }

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int age = year - cal.get(Calendar.YEAR);

        int monthBirth = cal.get(Calendar.MONTH);
        if (month == monthBirth) {
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            if (dayOfMonth < dayOfMonthBirth) {
                // 如果生日在当月，但是未达到生日当天的日期，年龄减一
                age--;
            }
        } else if (month < monthBirth) {
            // 如果当前月份未达到生日的月份，年龄计算减一
            age--;
        }

        return age;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.firstDate(new Date()).fromDate("yyyy-MM-dd HH:mm:ss"));
    }
}
