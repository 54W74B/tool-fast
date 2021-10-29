package com.intters.util;

import cn.hutool.core.map.MapUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * cookie工具类
 *
 * @author Ruison
 * @date 2018/7/16.
 */
public class CookieUtil {

    /**
     * 设置cookie
     *
     * @param response {@link HttpServletResponse}
     * @param name     cookie名称
     * @param value    cookie值
     * @param maxAge   cookie过期时间
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     * @param response {@link HttpServletResponse}
     * @param name     cookie名称
     */
    public static void remove(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     *
     * @param request {@link HttpServletRequest}
     * @param name    cookie名称
     * @return {@link Cookie}
     */
    public static Cookie get(HttpServletRequest request,
                             String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        return cookieMap.getOrDefault(name, null);
    }

    /**
     * 将cookie封装成Map
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Cookie}
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = MapUtil.newHashMap(MapUtil.DEFAULT_INITIAL_CAPACITY);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
