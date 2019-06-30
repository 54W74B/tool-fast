package com.intters.util;

import org.springframework.lang.Nullable;

/**
 * 对象工具类
 *
 * @author Ruison
 * @date 2019/4/6.
 */
public class ObjectUtil {

    /**
     * 判断元素不为空
     * @param obj object
     * @return boolean
     */
    public static boolean isNotEmpty(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        return true;
    }
}
