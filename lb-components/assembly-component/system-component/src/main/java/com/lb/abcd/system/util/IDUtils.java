package com.lb.abcd.system.util;

/**
 * @ClassName IDUtils
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 11:20
 * @Version 1.0
 */
public class IDUtils {

    /**
     * 生成ID-String
     * @return String
     */
    public static String generateId()
    {
        SnowFlakeUtil snowFlake = SnowFlakeUtil.getInstance();
        return String.valueOf(snowFlake.nextId());
    }

    /**
     * 生成ID-long
     * @return long
     */
    public static long generateLongId()
    {
        SnowFlakeUtil snowFlake = SnowFlakeUtil.getInstance();
        return snowFlake.nextId();
    }
}
