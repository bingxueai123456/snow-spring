package com.ice.controller;

import com.ice.service.TestSqlService;
import com.xiliulou.cache.redis.RedisService;
import com.xiliulou.core.web.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : eclair
 * @date : 2024/6/23 18:55
 */
@RestController
public class JsonTestController {
    @Autowired
    RedisService redisService;
    @Autowired
    TestSqlService testSqlService;

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

    @GetMapping("/test/sql/{id}")
    public R testSql(@PathVariable("id") Integer id) {
        if (id == 1) {
            testSqlService.testUpdateSQl1();
        } else if (id == 2) {
            testSqlService.testUpdateSqlLock();
        } else if (id == 3) {
            testSqlService.testUpdateLong();
        } else if (id == 4) {
            testSqlService.testSelectListLong();
        } else {
            testSqlService.testSelect();
        }
        return R.ok();
    }

    @GetMapping("/test/time/{t}")
    public R testTime(@PathVariable("t") Long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return R.ok();
    }

    static boolean condition = true;  // 条件变量，控制任务的继续或停止

    public static void main(String[] args) {
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//        // 定义任务，每次任务完成后再调度下次执行
//        Runnable task = new Runnable() {
//            @Override
//            public void run() {
//                if (condition) {
//                    System.out.println("条件满足，正在执行任务...");
//                    // 重新调度任务3秒后执行
//                    executorService.schedule(this, 3, TimeUnit.SECONDS);
//                } else {
//                    System.out.println("条件不再满足，任务结束。");
//                }
//            }
//        };
//        // 立即执行第一次任务
//        executorService.schedule(task, 0, TimeUnit.SECONDS);
//        // 模拟一段时间后条件变为false
//        try {
//            Thread.sleep(10000);  // 等待10秒，之后更改条件
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        condition = false;  // 修改条件，停止任务

    }

}
