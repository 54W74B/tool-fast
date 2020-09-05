package com.intters.util.redis.jedis;

import com.intters.util.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Jedis操作工具类
 *
 * @author Ruison
 * @date 2018/8/6.
 */
public class JedisUtil {

    private static JedisPool JEDIS_POOL;

    private static Jedis jedis = null;

    private static JedisUtil JEDIS_UTIL = null;

    public JedisUtil() {
    }

    /**
     * 构造函数
     *
     * @param jedisPool {@link JedisPool}
     */
    public JedisUtil(JedisPool jedisPool) {
        JEDIS_POOL = jedisPool;
        jedis = JEDIS_POOL.getResource();
    }

    /**
     * 初始化Jedis
     *
     * @param jedisPool {@link JedisPool}
     * @return Jedis工具类
     */
    public static JedisUtil init(JedisPool jedisPool) {
        JEDIS_UTIL = new JedisUtil(jedisPool);
        return JEDIS_UTIL;
    }

    /**
     * 获取Jedis
     *
     * @return {@link Jedis}
     */
    private static Jedis getJedis() {
        if (jedis == null) {
            jedis = JEDIS_POOL.getResource();
        }
        return jedis;
    }

    /**
     * 关闭Jedis
     */
    private static void close() {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 设置
     *
     * @param key   键名称
     * @param value 键值
     * @param time  过期时间
     */
    public static void set(String key, String value, int time) {
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
     * 获取值
     *
     * @param key 键名称
     * @return 值
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
     *
     * @param key   键名称
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
     *
     * @param key 键名称
     * @return 反序列化处理后的对象
     */
    public static Object get(byte[] key) {
        try {
            Jedis jedis = getJedis();
            return SerializeUtil.unSerialize(jedis.get(key));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    /**
     * 删除
     *
     * @param key 键名称
     */
    public static void del(Object key) {
        try {
            Jedis jedis = getJedis();
            if (key instanceof String) {
                jedis.del((String) key);
            } else if (key instanceof byte[]) {
                jedis.del((byte[]) key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
