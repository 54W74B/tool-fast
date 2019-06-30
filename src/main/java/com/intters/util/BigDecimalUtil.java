package com.intters.util;

import java.math.BigDecimal;

/**
 * BigDecimal 工具类 (金钱计算)
 * @author Ruison
 * @date 2018/7/12.
 */
public class BigDecimalUtil {

    /**
     * 保留几位小数
     */
    private final static int scale = 2;

    /**
     * 相加
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
        return b1.add(b2);
    }

    /**
     * 相减
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
        return b1.subtract(b2);
    }

    /**
     * 乘法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
        return b1.multiply(b2);
    }

    /**
     * 除法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
        //四舍五入法，保留两位小数
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 判断是否相等
     * @param v1
     * @param v2
     * @return
     */
    public static boolean compareTo(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2) == 0 ? true : false;
    }
}
