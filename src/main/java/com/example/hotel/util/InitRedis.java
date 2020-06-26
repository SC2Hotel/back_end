package com.example.hotel.util;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class InitRedis implements CommandLineRunner {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public void run(String... args){
        log.info("Begin to clean the redis");
        Set<String> keys = redisUtil.keys();
        for (String s: keys) {
            redisUtil.delete(s);
        }
        log.info("clean the redis successfully");
    }

}
