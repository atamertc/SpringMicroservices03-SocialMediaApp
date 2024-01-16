package com.atamertc.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @EnableCaching ile Spring Redis mekanizmasını kullanarak cachleme yapabilir hale gelir.
 * @EnableRedisRepositories ile Spring Redisin database olarak kullanılmasını sağlar.
 */
@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisConfig {
    /**
     * LettuceConnectionFactory: Spring Framework Redis ile birlikte kullanılmasını sağlar.
     * Springin Redise bağlanması için gerekli sınıftır.
     * <p>
     * RedisStandaloneConfiguration: Redis sunucusunun configurasyonu yapılır.
     *
     * @return
     */
    @Value("${redis.host}")
    private String hostname;
    @Value("${redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(hostname, port));
    }

}
