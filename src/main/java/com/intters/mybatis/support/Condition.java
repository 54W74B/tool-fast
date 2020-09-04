package com.intters.mybatis.support;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        Page<T> page = new Page<>(query.getCurrent(), query.getSize());
        page.setAsc(StrUtil.toStrArray(SqlKeyword.filter(query.getAscs())));
        page.setDesc(StrUtil.toStrArray(SqlKeyword.filter(query.getDescs())));
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
            SqlKeyword.buildCondition(query, qw, clazz);
            query.clear();
        }
        return qw;
    }


    public static void main(String[] args) {
        String str = "parentId";
        System.out.println(str.toUpperCase().contains(ID));
    }
}
