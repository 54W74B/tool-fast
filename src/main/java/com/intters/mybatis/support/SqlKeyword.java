package com.intters.mybatis.support;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.intters.util.StringPool;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 定义常用的 sql关键字
 *
 * @author Ruison
 * @date 2020/8/29-18:21
 */
public class SqlKeyword {
    private final static String SQL_REGEX = "'|%|--|insert|delete|select|count|group|union|drop|truncate|alter|grant|execute|exec|xp_cmdshell|call|declare|sql";

    private static final String EQUAL = "_equal";
    private static final String NOT_EQUAL = "_notequal";
    private static final String LIKE = "_like";
    private static final String NOT_LIKE = "_notlike";
    private static final String GT = "_gt";
    private static final String LT = "_lt";
    private static final String DATE_GT = "_dategt";
    private static final String DATE_EQUAL = "_dateequal";
    private static final String DATE_LT = "_datelt";
    private static final String IS_NULL = "_null";
    private static final String NOT_NULL = "_notnull";
    private static final String IGNORE = "_ignore";

    /**
     * 条件构造器
     *
     * @param query 查询字段
     * @param qw    查询包装类
     * @param clazz 类
     * @param <T>   泛型
     */
    public static <T> void buildCondition(Map<String, Object> query, QueryWrapper<?> qw, Class<T> clazz) {
        if (query.isEmpty()) {
            return;
        }
        query.forEach((k, v) -> {
            if (ObjectUtil.hasEmpty(k, v) || k.endsWith(IGNORE)) {
                return;
            }
            if (k.endsWith(LIKE)) {
                String column = getColumn(k, LIKE);
                if (clazz == null || classContainProperties(clazz, column)) {
                    qw.like(column, v);
                }
            } else if (k.endsWith(NOT_EQUAL)) {
                String column = getColumn(k, NOT_EQUAL);
                if (clazz == null || classContainProperties(clazz, column)) {
                    qw.ne(column, v);
                }
            } else if (k.endsWith(NOT_LIKE)) {
                String column = getColumn(k, NOT_LIKE);
                if (clazz == null || classContainProperties(clazz, column)) {
                    qw.notLike(column, v);
                }
            } else if (k.endsWith(GT)) {
                String column = getColumn(k, GT);
                if (clazz == null || classContainProperties(clazz, column)) {
                    qw.gt(column, v);
                }
            } else if (k.endsWith(LT)) {
                String column = getColumn(k, LT);
                if (clazz == null || classContainProperties(clazz, column)) {
                    qw.lt(column, v);
                }
            } else if (k.endsWith(DATE_GT)) {
                String column = getColumn(k, DATE_GT);
                if (clazz == null || classContainProperties(clazz, column)) {
                    qw.gt(column, v);
                }
            } else if (k.endsWith(DATE_EQUAL)) {
                String column = getColumn(k, DATE_EQUAL);
                if (clazz == null || classContainProperties(clazz, column)) {
                    qw.eq(column, v);
                }
            } else if (k.endsWith(DATE_LT)) {
                String column = getColumn(k, DATE_LT);
                if (clazz == null || classContainProperties(clazz, column)) {
                    qw.lt(column, v);
                }
            } else if (k.endsWith(IS_NULL)) {
                String column = getColumn(k, IS_NULL);
                if (clazz == null || classContainProperties(clazz, column)) {
                    qw.isNull(column);
                }
            } else if (k.endsWith(NOT_NULL)) {
                String column = getColumn(k, NOT_NULL);
                if (clazz == null || classContainProperties(clazz, column)) {
                    qw.isNotNull(column);
                }
            } else {
                String column = getColumn(k, EQUAL);
                if (clazz == null || classContainProperties(clazz, column)) {
                    qw.eq(column, v);
                }
            }
        });
    }

    /**
     * 获取数据库字段
     *
     * @param column  字段名
     * @param keyword 关键字
     * @return String
     */
    private static String getColumn(String column, String keyword) {
        return StrUtil.toUnderlineCase(StrUtil.removeSuffix(column, keyword));
    }

    /**
     * 把SQL关键字替换为空字符串
     *
     * @param param 关键字
     * @return string
     */
    public static String filter(String param) {
        if (param == null) {
            return null;
        }
        return param.replaceAll("(?i)" + SQL_REGEX, StringPool.EMPTY);
    }

    /**
     * 判断属性是不是属于该类得属性
     *
     * @param clazz     类
     * @param attribute 属性
     * @param <T>       泛型
     * @return true or false
     */
    private static <T> boolean classContainProperties(Class<T> clazz, String attribute) {
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals(attribute)) {
                return true;
            }
        }
        return false;
    }
}
