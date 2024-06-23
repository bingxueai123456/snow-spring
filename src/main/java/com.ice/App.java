package com.ice;

import com.xiliulou.cache.redis.EnableRedis;
import com.xiliulou.db.dynamic.annotation.EnableDynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author : eclair
 * @date : 2024/6/21 17:25
 */
@SpringBootApplication
@EnableDynamicDataSource
@EnableRedis
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
