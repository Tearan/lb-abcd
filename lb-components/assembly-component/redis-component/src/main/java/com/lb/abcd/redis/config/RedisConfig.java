package com.lb.abcd.redis.config;

import com.lb.abcd.redis.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @ClassName RedisConfig
 * @Description 配置redis缓存管理器
 * @Author Terran
 * @Date 2021/1/31 14:58
 * @Version 1.0
 */

/*
@Configuration
*/
/** 系统中有RedisOperations类时*//*

@ConditionalOnClass(RedisOperations.class)
*/
/** 启动RedisProperties这个类*//*

@EnableConfigurationProperties(RedisProperties.class)
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                */
/** 1分钟缓存失效*//*

                .entryTtl(Duration.ofSeconds(60))
                */
/** 设置key的序列化方式*//*

                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                */
/** 设置value的序列化方式*//*

                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer(Object.class)))
                */
/** 不缓存null值*//*

                .disableCachingNullValues();
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
        return redisCacheManager;
    }
}
*/
