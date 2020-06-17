package com.example.hotel.config;


import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/6/16 22:43
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {
    @Bean
    public RedisTemplate<Integer, User> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Integer, User> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);


        Jackson2JsonRedisSerializer<User> ser = new Jackson2JsonRedisSerializer<>(User.class);
        template.setDefaultSerializer(ser);
        return template;
    }
}