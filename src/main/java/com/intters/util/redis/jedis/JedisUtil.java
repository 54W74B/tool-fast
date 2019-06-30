package com.intters.util.redis.jedis;

import com.intters.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Ruison
 * @date 2018/8/6.
 */
public class JedisUtil {

    @Autowired
    private static JedisPool jedisPool;

    private static Jedis jedis = null;

    private static Jedis getJedis() {
        if (jedis == null) {
            jedis = jedisPool.getResource();
        }
        return jedis;
    }

    private static void close() {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 设置
     * @param key 键名称
     * @param value 键值
     * @param time 过期时间
     */
    public static void set(String key, String value, int time){
        try {
            Jedis jedis = getJedis();
            jedis.set(key, value);
            if (time > 0) {
                jedis.expire(key, time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * 获取
     * @param key 键名称
     * @return
     */
    public static String get(String key) {
        try {
            Jedis jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    /**
     * 设置对象（经过序列化处理）
     * @param key 键名称
     * @param value 键值
     */
    public static void set(byte[] key, Object value) {
        try {
            Jedis jedis = getJedis();
            jedis.set(key, SerializeUtil.serialize(value));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * 获取对象（经过反序列化处理）
     * @param key 键名称
     * @return
     */
    public static Object get(byte[] key) {
        try {
            Jedis jedis = getJedis();
            return SerializeUtil.unserialize(jedis.get(key));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    /**
     * 删除
     * @param key
     */
    public static void del(Object key) {
        try {
            Jedis jedis = getJedis();
            if (key instanceof String) {
                jedis.del((String)key);
            } else if (key instanceof byte[]) {
                jedis.del((byte[])key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
