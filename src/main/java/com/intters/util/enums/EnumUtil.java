package com.intters.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举工具类
 *
 * @author Ruison
 * @date 2018/7/14.
 */
public class EnumUtil {

    /**
     * 枚举类中所有枚举对象的name列表
     *
     * @param clazz 枚举类
     * @return name列表
     */
    public static List<String> getNames(Class<? extends Enum<?>> clazz) {
        final Enum<?>[] enums = clazz.getEnumConstants();
        if (null == enums) {
            return null;
        }
        final List<String> list = new ArrayList<>(enums.length);
        for (Enum<?> e : enums) {
            list.add(e.name());
        }
        return list;
    }

    /**
     * 根据code获取枚举
     *
     * @param code int值
     * @param enumClass 枚举
     * @param <T> 泛型
     * @return 枚举
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }

    /**
     * 根据msg获取枚举
     *
     * @param msg String值
     * @param enumClass 枚举
     * @param <T> 泛型
     * @return 枚举
     */
    public static <T extends CodeEnum> T getByMsg(String msg, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (msg.equals(each.getMsg())) {
                return each;
            }
        }
        return null;
    }


}
