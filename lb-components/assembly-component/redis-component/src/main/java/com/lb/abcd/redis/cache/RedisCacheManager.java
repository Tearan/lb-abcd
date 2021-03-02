package com.lb.abcd.redis.cache;

import com.lb.abcd.redis.util.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName RedisCacheManager
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 11:28
 * @Version 1.0
 */
public class RedisCacheManager implements CacheManager {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache<>(redisUtil);
    }
}
