package com.intters.util.mybatis.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.intters.util.NumberUtil;
import com.intters.util.ObjectUtil;
import com.intters.util.StrUtil;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * 分页工具
 *
 * @author Ruison
 * @date 2019/4/6.
 */
public class Condition {

    /**
     * 转化成mybatis plus中的Page
     *
     * @param query
     * @return
     */
    public static <T> IPage<T> getPage(Query query) {
        Page<T> page = new Page(NumberUtil.toInt(query.getCurrent(), 1), NumberUtil.toInt(query.getSize(), 10));
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
        query.remove("current");
        query.remove("size");
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.setEntity(BeanUtils.instantiateClass(clazz));
        if (ObjectUtil.isNotEmpty(query)) {
            query.forEach((k, v) -> {
                if (ObjectUtil.isNotEmpty(v)) {
                    qw.like(StrUtil.humpToUnderline(k), v);
                }
            });
        }
        return qw;
    }
}
