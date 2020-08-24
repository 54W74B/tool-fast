package com.intters.mybatis.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.intters.util.NumberUtil;
import com.intters.util.ObjectUtil;
import com.intters.util.StrUtil;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 分页工具
 *
 * @author Ruison
 * @date 2019/4/6.
 */
public class Condition {

    public final static String ID = "ID";

    /**
     * 转化成mybatis plus中的Page
     *
     * @param query
     * @return
     */
    public static <T> IPage<T> getPage(Query query) {
        Page<T> page = new Page<>(NumberUtil.toInt(query.getCurrent(), 1), NumberUtil.toInt(query.getSize(), 10));
        page.setAsc(StrUtil.toStrArray(query.getAscs()));
        page.setDesc(StrUtil.toStrArray(query.getDescs()));
        return page;
    }

    /**
     * 获取mybatis plus中的QueryWrapper
     *
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
        return new QueryWrapper<>(entity);
    }

    /**
     * 获取mybatis plus中的QueryWrapper
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Class<T> clazz) {
        QueryWrapper<T> qw = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(query)) {
            query.remove("current");
            query.remove("size");
            qw.setEntity(BeanUtils.instantiateClass(clazz));
            query.forEach((k, v) -> {
                if (ObjectUtil.isNotEmpty(v)) {
                    if (equal(clazz, k)) {
                        // 判断是否是查询还是模糊查询
                        if (k.toUpperCase().contains(ID)) {
                            qw.eq(StrUtil.humpToUnderline(k), v);
                        } else {
                            qw.like(StrUtil.humpToUnderline(k), v);
                        }
                    }
                }
            });
            query.clear();
        }
        return qw;
    }

    /**
     * 判断属性是不是属于该类得属性
     *
     * @param clazz 类
     * @param attribute 属性
     * @return true or false
     */
    private static  boolean equal(Class clazz, String attribute) {
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals(attribute)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String str = "parentId";
        System.out.println(str.toUpperCase().contains(ID));
    }
}
