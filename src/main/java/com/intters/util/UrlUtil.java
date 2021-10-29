package com.intters.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Url Encode
 *
 * @author Ruison
 * @date 2018/8/17.
 */
public class UrlUtil {

    public final static String ENCODE = "UTF-8";

    /**
     * 加密
     *
     * @param url 待加密的URL
     * @return 加密的URL
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    public static String encode(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, ENCODE);
    }

    /**
     * 解密
     *
     * @param url 待解密的URL
     * @return 解密的URL
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    public static String decoder(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, ENCODE);
    }
}
