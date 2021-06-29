package com.jrx.cloud.common.util;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author hongjc
 * @version 1.0  2020/6/9
 */
public class SnowflakeUtils {

    private static final long START_TIME = 1420041600000L;
    private static long LAST_TIME_STAMP = -1L;

    private static final int TIME_LEN = 41;
    private static final int DATA_LEN = 5;
   private static final int WORK_LEN = 5;
    private static final int SEQ_LEN = 12;

    private static final int TIME_LEFT_BIT = 64 - 1 - TIME_LEN;

    private static final int DATA_MAX_NUM = ~(-1 << DATA_LEN);
    private static final int WORK_MAX_NUM = ~(-1 << WORK_LEN);

    private static final int DATA_RANDOM = DATA_MAX_NUM + 1;
    private static final int WORK_RANDOM = WORK_MAX_NUM + 1;

    private static final int DATA_LEFT_BIT = TIME_LEFT_BIT - DATA_LEN;
    private static final int WORK_LEFT_BIT = DATA_LEFT_BIT - WORK_LEN;

    private static long LAST_SEQ = 0L;

    private static final long SEQ_MAX_NUM = ~(-1 << SEQ_LEN);

    private static final long DATA_ID = getDataId();
    private static final long WORK_ID = getWorkId();

    public synchronized static long nextId() {
        var now = System.currentTimeMillis();

        if (now < LAST_TIME_STAMP) {
            throw new RuntimeException(String.format("System time error.Reject the build in %d milliseconds", START_TIME - now));
        }

        if (now == LAST_TIME_STAMP) {
            LAST_SEQ = (LAST_SEQ + 1) & SEQ_MAX_NUM;
            if (LAST_SEQ == 0) {
                now = nextMillis(LAST_TIME_STAMP);
            }
        } else {
            LAST_SEQ = 0;
        }

        LAST_TIME_STAMP = now;
        return ((now - START_TIME) << TIME_LEFT_BIT) | (DATA_ID << DATA_LEFT_BIT) | (WORK_ID << WORK_LEFT_BIT) | LAST_SEQ;
    }

    // ----------------------------------------< Private Method >----------------------------------------
    private static long nextMillis(long lastMillis) {
        var now = System.currentTimeMillis();
        while (now <= lastMillis) {
            now = System.currentTimeMillis();
        }
        return now;
    }

    private static int getHostId(String hostAddress, int max) {
        var bytes = hostAddress.getBytes();
        var sums = 0;
        for (var b : bytes) {
            sums += b;
        }
        return sums % (max + 1);
    }

    private static int getWorkId() {
        try {
            return getHostId(Inet4Address.getLocalHost().getHostAddress(), WORK_MAX_NUM);
        } catch (UnknownHostException e) {
            return new Random().nextInt(WORK_RANDOM);
        }
    }

    private static int getDataId() {
        try {
            return getHostId(Inet4Address.getLocalHost().getHostName(), DATA_MAX_NUM);
        } catch (UnknownHostException e) {
            return new Random().nextInt(DATA_RANDOM);
        }
    }
}
