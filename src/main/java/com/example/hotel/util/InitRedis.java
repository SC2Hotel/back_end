package com.example.hotel.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/6/25 14:41
 */
@Component
public class InitRedis implements CommandLineRunner {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Begin to clean the redis");
        Set<String> keys = redisUtil.keys();
        Iterator<String> it = keys.iterator();
        while(it.hasNext()){
            redisUtil.delete(it.next());
        }
        System.out.println("clean the redis successfully");
    }

}
