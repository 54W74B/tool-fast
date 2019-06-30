package com.intters.util.idworker;

import com.intters.util.idworker.strategy.DefaultWorkerIdStrategy;
import com.intters.util.idworker.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 高性能、相同几率几乎为零的ID生成工具
 * 支持分布式，单机模式，可独立部署
 * 感谢snowflake提供部分算法灵感
 *
 * @author Ruison
 * @date 2018/8/6.
 */
public class RuiD {
    private static WorkerIdStrategy workerIdStrategy;
    private static IdWorker idWorker;

    static {
        configure(DefaultWorkerIdStrategy.instance);
    }


    public static synchronized void configure(WorkerIdStrategy custom) {
        if (workerIdStrategy != null) {
            workerIdStrategy.release();
        }
        workerIdStrategy = custom;
        idWorker = new IdWorker(workerIdStrategy.availableWorkerId()) {
            @Override
            public long getEpoch() {
                return Utils.midnightMillis();
            }
        };
    }

    /**
     * 一天最大毫秒86400000，最大占用27比特
     * 27+10+11=48位 最大值281474976710655(15字)，YK0XXHZ827(10字)
     * 6位(YYMMDD)+15位，共21位
     *
     * @return 固定21位数字字符串
     */

    public static String next() {
        long id = idWorker.nextId();
        String yyMMdd = new SimpleDateFormat("yyMMdd").format(new Date());
        return yyMMdd + String.format("%014d", id);
    }


    /**
     * 返回固定16位的字母数字混编的字符串。
     */
    public static String nextShort() {
        long id = idWorker.nextId();
        String yyMMdd = new SimpleDateFormat("yyMMdd").format(new Date());
        return yyMMdd + Utils.padLeft(Utils.encode(id), 10, '0');
    }
    
    public static void main(String[] args) {
		String aa = RuiD.nextShort();
		String bb = RuiD.next();

		System.out.println(aa);
		System.out.println(bb);
	}
}
