package com.intters.util;

import java.util.*;

/**
 * 字符串工具类
 *
 * @author Ruison
 * @date 2018/7/14.
 */
public class StrUtil<T> {

    /**
     * 拼接字符串
     * 采用Stringbuffer，避免过程产生太多的String对象
     *
     * @param val 需要拼接的字符串
     * @return 字符串
     */
    public static String splice(String... val) {
        if (val == null) {
            return null;
        }
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < val.length; i++) {
            str.append(val[i]);
        }
        return str.toString();
    }

    /**
     * 根据分隔符拼接字符串
     * StringJoiner解决低效率的字符串拼接
     *
     * @param delimiter 分隔符
     * @param val 需要拼接的字符串
     * @return
     */
    public static String spliceSepar(String delimiter, String... val) {
        StringJoiner str = new StringJoiner(delimiter);
        for (int i = 0; i < val.length; i++) {
            str.add(val[i]);
        }
        return str.toString();
    }

    /**
     * 根据字符串分割字符串
     * 如果只是根据字符分割字符串，采用StringTokenizer效率会更高
     * 如果分割空格等字符串，采用split效率会更好
     *
     * @param val
     * @return
     */
    public static List<String> segment(String delimiter, String... val) {
        if (val == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < val.length; i++) {
            StringTokenizer str = new StringTokenizer(val[i], delimiter);
            while (str.hasMoreElements()) {
                list.add(str.nextToken());
            }
        }
        return list;
    }

    /**
     * 转换为String数组<br>
     *
     * @param str 被转换的值
     * @return 结果
     */
    public static String[] toStrArray(String str) {
        return toStrArray(",", str);
    }

    /**
     * 转换为String数组<br>
     *
     * @param split 分隔符
     * @param val   被转换的值
     * @return 结果
     */
    public static String[] toStrArray(String split, String val) {
        if (val == null) {
            return new String[]{};
        }
        return val.indexOf(",") == 0 ? new String[]{val} : val.split(split);
    }

    public static List<String> splitStr(String val) {
        return splitStr(",", val);
    }

    /**
     * 根据字符串分割字符串
     * 遇到 '/n' '/t'等，采用split效率会更好
     *
     * @param delimiter
     * @param val
     * @return String
     */
    public static List<String> splitStr(String delimiter, String... val) {
        if (val == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < val.length; i++) {
            list.addAll(Arrays.asList(val[i].split(delimiter)));
        }
        return list;
    }

    /**
     * 根据字符串分割字符串
     * 遇到 '/n' '/t'等，采用split效率会更好
     *
     * @param delimiter
     * @param val
     * @return Integer
     */
    public static List<String> splitInt(String delimiter, String... val) {
        if (val == null) {
            return null;
        }
        List<String> lists = new ArrayList<>();
        for (String v : val) {
            String[] wait = v.split(delimiter);
            lists.addAll(Arrays.asList(wait));
        }

        return lists;
    }

    /**
     * 为空赋予默认值
     *
     * @param val 判断是否为空的值
     * @param defaultVal 默认值
     * @return 返回String类型不为空的字符串
     */
    public static String getDefaultValue(Object val, Object defaultVal) {
        if (defaultVal != null) {
            return val == null ? defaultVal.toString() : val.toString();
        }
        return val == null ? "" : val.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param para 字符串
     * @return String
     */
    public static String humpToUnderline(String para) {
        para = lowerFirst(para);
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 首字母变小写
     *
     * @param str 字符串
     * @return {String}
     */
    public static String lowerFirst(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= StringPool.U_A && firstChar <= StringPool.U_Z) {
            char[] arr = str.toCharArray();
            arr[0] += (StringPool.L_A - StringPool.U_A);
            return new String(arr);
        }
        return str;
    }

    public static void main(String[] args) {
        String[] a = StrUtil.toStrArray(",", "1,2");
        for (String s : a) {
            System.out.println(s);
        }
    }
}
