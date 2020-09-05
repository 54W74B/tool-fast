package com.intters.mybatis.support;

import cn.hutool.core.map.MapBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * @author Ruison
 * @date 2020/9/4-18:11
 */
public class SqlKeywordTest {

    @Test
    public void buildCondition() {
        SqlKeyword.buildCondition(MapBuilder.create(new HashMap<String, Object>(4))
                        .put("id", 1)
                        .put("name_null", "Ruison")
                        .map(),
                new QueryWrapper<>(),
                null);
    }
}