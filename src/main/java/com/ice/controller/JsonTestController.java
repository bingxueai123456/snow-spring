package com.ice.controller;

import com.xiliulou.cache.redis.RedisService;
import com.xiliulou.core.web.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : eclair
 * @date : 2024/6/23 18:55
 */
@RestController
public class JsonTestController {
    @Autowired
    RedisService redisService;

    @GetMapping("/test")
    public R test() {
        RedisScript redisScript = RedisScript.of(
                "local times = redis.call('incr',KEYS[1]) if times == 1 then redis.call('expire',KEYS[1],ARGV[1]) end if times > tonumber(ARGV[2]) then return 0 end return 1",
                Long.class);
        List<String> list = new ArrayList<>(1);
        list.add("test");
        Object result = redisService.getRedisTemplate().execute(redisScript, list, String.valueOf(ChronoUnit.SECONDS.between(LocalDateTime.now(), LocalDate.now().plusDays(1).atStartOfDay())),
                "15");
        System.out.println(result);
        return R.ok();
    }
}
