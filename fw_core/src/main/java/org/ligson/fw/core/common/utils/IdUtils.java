package org.ligson.fw.core.common.utils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ligson on 2016/4/27.
 */
public class IdUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String randomNum(int num) {
        int random = (int) (Math.random() * Math.pow(10, num));
        String randomString = random + "";
        int fixNum = num - randomString.length();
        StringBuilder builder = new StringBuilder(randomString);
        for (int i = 0; i < fixNum; i++) {
            builder.insert(0, "0");
        }
        return builder.toString();
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String randomIntString() {
        String prefix = sdf.format(new Date());
        prefix = prefix + randomNum(5);
        return prefix;
    }

    public static BigInteger randomBigInt() {
        return new BigInteger(randomIntString());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            System.out.println(randomBigInt());
        }
    }

}
