package com.example.hotel.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/6/16 23:31
 */
@Component
@Slf4j
public class RedisUtil {

    public static final String HOTEL_PAGE_PREFIX = "hotel:hotel_page:";

    public static final String HOTEL_KEY_NAME_PREFIX = "hotel:hotel:";

    public static final String ROOM_KEY_NAME_PREFIX = "hotel:room:";

    public static final String COUPON_KEY_NAME_PREFIX = "hotel:coupon:";

    public static final String USER_INFO_PREFIX = "hotel:user:";

    @Autowired
    RedisTemplate redisTemplate;

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
            log.error(e.getMessage());
        }
        return result;
    }

    public boolean delete(String key){
        return redisTemplate.delete(key);
    }

    /**
     * 设置key的有效时间，单位为秒
     * @param key
     * @param exTime
     */
    public void expire(String key,long exTime){
        try {
            redisTemplate.expire(key,exTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void clean(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    public Set<String> keys(){
        return redisTemplate.keys("*");
    }
}
