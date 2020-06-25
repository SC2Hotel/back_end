package com.example.hotel.config;

import com.example.hotel.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/6/25 11:18
 */
public class InitConfig {

    @Autowired
    RedisUtil redisUtil;

    @PostConstruct
    public void postConstruct(){



    }

}
