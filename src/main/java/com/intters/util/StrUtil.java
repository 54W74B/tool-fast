package com.intters.util;

import cn.hutool.core.util.NumberUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 *
 * @author Ruison
 * @date 2018/7/14.
 */
public class StrUtil {

    /**
     * 拼接字符串
     * 采用StringBuilder，避免过程产生太多的String对象
     *
     * @param val 需要拼接的字符串
     * @return 字符串
     */
    public static String splice(String... val) {
        if (val == null) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        for (String s : val) {
            str.append(s);
        }
        return str.toString();
    }

    /**
     * 根据分隔符拼接字符串
     * StringJoiner解决低效率的字符串拼接
     *
     * @param delimiter 分隔符
     * @param val       需要拼接的字符串
     * @return 拼接好的字符串
     */
    public static String splice(String delimiter, String... val) {
        if (val == null) {
            return null;
        }
        StringJoiner str = new StringJoiner(delimiter);
        for (String s : val) {
            str.add(s);
        }
        return str.toString();
    }

    /**
     * 根据字符串分割字符串
     * 如果只是根据字符分割字符串，采用StringTokenizer效率会更高
     * 如果分割空格等字符串，采用split效率会更好
     *
     * @param delimiter 分隔符
     * @param val       需要分割得字符串
     * @return 分割好的字符串数组
     */
    public static List<String> segment(String delimiter, String... val) {
        if (val == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (String s : val) {
            StringTokenizer str = new StringTokenizer(s, delimiter);
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
     * @param delimiter 分隔符
     * @param val 需要分割得字符串
     * @return 分割好的字符串数组
     */
    public static List<String> splitStr(String delimiter, String... val) {
        if (val == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (String s : val) {
            list.addAll(Arrays.asList(s.split(delimiter)));
        }
        return list;
    }

    /**
     * 分割字符串并转换成数字
     * 遇到 '/n' '/t'等，采用split效率会更好
     *
     * @param delimiter 分隔符
     * @param val 需要分割得字符串
     * @return Integer
     */
    public static List<Integer> splitInt(String delimiter, String... val) {
        if (val == null) {
            return null;
        }
        List<String> lists = new ArrayList<>();
        for (String s : val) {
            lists.addAll(Arrays.asList(s.split(delimiter)));
        }
        return lists.stream().filter(NumberUtil::isNumber).map(Integer::valueOf).collect(Collectors.toList());
    }

    /**
     * 为空赋予默认值
     *
     * @param val        判断是否为空的值
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
     * @return 首字母小写的字符串
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
}
