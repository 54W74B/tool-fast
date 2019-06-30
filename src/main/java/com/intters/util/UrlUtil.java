package com.intters.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Url Encode
 * @author Ruison
 * @date 2018/8/17.
 */
public class UrlUtil {

    private final static String encode = "UTF-8";

    /**
     * 加密
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, encode);
    }

    /**
     * 解密
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decoder(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, encode);
    }
}
