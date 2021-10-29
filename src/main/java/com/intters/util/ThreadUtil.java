package com.intters.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程工具类
 * 不共享线程，是线程安全
 *
 * @author Ruison
 * @date 2018/7/16.
 */
public class ThreadUtil {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();
    /**
     * 默认线程数
     */
    private static final int DEFAULT_THREADS_NUMBER = 6;

    /**
     * 指定键指定的值
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(DEFAULT_THREADS_NUMBER);
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    /**
     * 返回到其指定的键被映射的值
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    /**
     * 删除当前线程局部变量的值
     */
    public static void remove() {
        Map<String, Object> map = threadLocal.get();
        if (map != null) {
            threadLocal.remove();
        }
    }
}
