package com.tomo.config;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableCaching
@EnableConfigurationProperties(RedisProperties.class)
public class RedisCacheConfig {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private Integer port;
    @Value("${spring.data.redis.username}")
    private String username;
    @Value("${spring.data.redis.password}")
    private String pwd;
    @Value("${spring.data.redis.timeout}")
    private Integer redisTimeout;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enablePeriodicRefresh(Duration.ofMillis(redisTimeout))
                .build();
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .commandTimeout(Duration.ofMillis(redisTimeout))
                .clientOptions(ClusterClientOptions.builder().topologyRefreshOptions(topologyRefreshOptions).build())
                .useSsl()
                .build();

        // Redis 连接配置
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(Arrays.asList(host + ":" + port));
        clusterConfiguration.setUsername(username);
        clusterConfiguration.setPassword(pwd);

        return new LettuceConnectionFactory(clusterConfiguration, clientConfig);
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> rTemplate = new RedisTemplate<>();
        rTemplate.setKeySerializer(new StringRedisSerializer());
        rTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        rTemplate.setConnectionFactory(connectionFactory);
        rTemplate.afterPropertiesSet();
        return rTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 配置第一个缓存的 RedisCacheConfiguration，过期时间为 10 秒
        RedisCacheConfiguration tenSecondsCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(20))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));


        // 配置第二个缓存的 RedisCacheConfiguration，过期时间为 5 分钟
        RedisCacheConfiguration fiveMinutesCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));


        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues())
                .withCacheConfiguration("TokenCache", tenSecondsCacheConfig)
                .withCacheConfiguration("KLineCache", fiveMinutesCacheConfig)
                .build();
    }
}