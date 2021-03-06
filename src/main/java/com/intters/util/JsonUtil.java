package com.intters.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON工具类
 *
 * @author Ruison
 * @date 2018/7/16.
 */
public class JsonUtil {

    /**
     * 转化为对象
     *
     * @param json  json数据
     * @param clazz 对象
     * @param <T>   泛型对象
     * @return 反序列化的对象
     */
    public static <T> T toJsonBean(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 转换成Json对象
     * FastJson转化json效率高于GSON
     *
     * @param object 对象
     * @return 序列化的对象
     */
    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
