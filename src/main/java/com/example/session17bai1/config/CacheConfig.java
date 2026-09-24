package com.example.session17bai1.config;

import java.time.Duration;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.lettuce.core.resource.ClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

@Configuration
public class CacheConfig {

    private final RedisProperties redisProperties;

    public CacheConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName(redisProperties.getHost());
        standaloneConfig.setPort(redisProperties.getPort());
        if (redisProperties.getPassword() != null && !redisProperties.getPassword().isBlank()) {
            standaloneConfig.setPassword(redisProperties.getPassword());
        }

        LettuceClientConfiguration clientConfig = buildLettuceClientConfiguration();

        return new LettuceConnectionFactory(standaloneConfig, clientConfig);
    }

    private LettuceClientConfiguration buildLettuceClientConfiguration() {
        Duration commandTimeout = redisProperties.getTimeout() != null
                ? redisProperties.getTimeout()
                : Duration.ofSeconds(2);

        RedisProperties.Pool poolProps = redisProperties.getLettuce() != null
                ? redisProperties.getLettuce().getPool()
                : null;

        if (poolProps != null) {
            GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
            poolConfig.setMaxTotal(poolProps.getMaxActive());
            if (poolProps.getMaxIdle() > 0) {
                poolConfig.setMaxIdle(poolProps.getMaxIdle());
            }
            poolConfig.setMinIdle(poolProps.getMinIdle());
            if (poolProps.getMaxWait() != null && !poolProps.getMaxWait().isNegative()) {
                poolConfig.setMaxWait(poolProps.getMaxWait());
            }

            return LettucePoolingClientConfiguration.builder()
                    .commandTimeout(commandTimeout)
                    .poolConfig(poolConfig)
                    .clientResources(ClientResources.create())
                    .build();
        }

        return LettuceClientConfiguration.builder()
                .commandTimeout(commandTimeout)
                .build();
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration)
                .build();
    }
}
