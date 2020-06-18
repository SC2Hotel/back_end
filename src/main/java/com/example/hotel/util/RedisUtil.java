package com.example.hotel.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/6/16 23:31
 */
@Component
public class RedisUtil {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 这三个方法是针对<Integer, Object>
     */
    public boolean hasKey(Integer key){
        return redisTemplate.hasKey(key);
    }

    public Object get(Integer key){
        return redisTemplate.opsForValue().get(key);
    }

    public boolean set(final Integer key, Object value){
        boolean result = false;
        try{
            redisTemplate.opsForValue().set(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
    /**
     * 这三个方法是针对<String, Object>
     */

    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public boolean set(final String key, Object value){
        boolean result = false;
        try{
            redisTemplate.opsForValue().set(key, value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();;
        }
        return result;
    }

    public boolean delete(String key){
        return redisTemplate.delete(key);
    }

}
