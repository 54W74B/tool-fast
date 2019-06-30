package com.intters.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ruison
 * @date 2018/7/16.
 */
public class HeaderUtil {


    /**
     * 获取请求头内容
     *
     * @param request 当前请求的request
     * @param name 请求头名称
     * @param defaultName 默认
     * @return 内容
     */
    public static String get(HttpServletRequest request, String name, String defaultName) {
        return get(request, name, null, defaultName);
    }

    /**
     * 获取请求头内容
     *
     * @param request 当前请求的request
     * @param name 请求头名称
     * @param subLength 截取内容起始位置，适合截取携带Basic的token
     * @param defaultName 默认
     * @return 内容
     */
    public static String get(HttpServletRequest request, String name, Integer subLength, String defaultName) {
        String value = request.getHeader(name);
        if (value != null && subLength > 0) {
            value = value.substring(subLength);
        }
        if (StringUtils.isEmpty(value)) {
            value = request.getParameter(defaultName);
        }
        return value;
    }
}
