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

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static void remove(){
        threadLocal.remove();
    }
}
