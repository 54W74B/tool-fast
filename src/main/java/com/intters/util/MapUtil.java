package com.intters.util;

import java.util.*;

/**
 * Map工具类
 * @author Ruison
 * @date 2018/7/14.
 */
public class MapUtil {

    private static Map<Object, Object> map;

    static {
        if (map == null) {
            map = new HashMap<>();
        }
    }

    /**
     * 设置map
     * @param key 键名称
     * @param val 键值
     * @return map
     */
    public static Map set(Object key, Object val) {
        map.put(key, val);
        return map;
    }

    /**
     * 获取map某个键值
     * @param key 键名称
     * @return 键值
     */
    public static Object get(Object key) {
        return map.get(key);
    }

    /**
     * 获取Map键值
     * @return
     */
    public static List<String> getList() {
        List<String> list = new ArrayList<>();
        Set<Map.Entry<Object, Object>> entrySet = map.entrySet();
        for(Map.Entry<Object, Object> entry : entrySet){
            list.add(entry.getKey().toString());
        }
        return list;
    }

}
