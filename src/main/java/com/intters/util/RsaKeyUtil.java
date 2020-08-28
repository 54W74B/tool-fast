package com.intters.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA公私钥工具类
 *
 * @author Ruison
 * @date 2018/8/8.
 */
public class RsaKeyUtil {

    public static final String PRI_KEY_NAME = "pri";
    public static final String PUB_KEY_NAME = "pub";

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字节
     * @return 公钥
     * @throws InvalidKeySpecException  无效的密钥规范异常
     * @throws NoSuchAlgorithmException 不支持这样的算法异常
     */
    public PublicKey getPublicKey(byte[] publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字节
     * @return 私钥
     * @throws InvalidKeySpecException  无效的密钥规范异常
     * @throws NoSuchAlgorithmException 不支持这样的算法异常
     */
    public PrivateKey getPrivateKey(byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * 生成rsa公钥和私钥
     *
     * @param code 安全随机码
     * @return 公钥和私钥
     * @throws NoSuchAlgorithmException 不支持这样的算法异常
     */
    public static Map<String, byte[]> generateKey(String code) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(code.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        Map<String, byte[]> map = new HashMap<>(2);
        map.put(PUB_KEY_NAME, publicKeyBytes);
        map.put(PRI_KEY_NAME, privateKeyBytes);
        return map;
    }

    /**
     * 加密
     *
     * @param s 待加密字符串
     * @return 加密完的字符串
     */
    public static String toHexString(String s) {
        return (new BASE64Encoder()).encodeBuffer(s.getBytes());
    }

    /**
     * 解密
     *
     * @param s 待解密字符串
     * @return 解密完的字符串
     * @throws IOException 输入流异常
     */
    public static byte[] toBytes(String s) throws IOException {
        return (new BASE64Decoder()).decodeBuffer(s);
    }
}
