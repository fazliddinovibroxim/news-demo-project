package com.example.news_project.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration newsListCache = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60)); // TTL for news list

        RedisCacheConfiguration newsSingleCache = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(120)); // TTL for single news

        return RedisCacheManager.builder(connectionFactory)
                .withCacheConfiguration("news_list", newsListCache)
                .withCacheConfiguration("news_single", newsSingleCache)
                .build();
    }
}
