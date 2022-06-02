package com.yang.concurrent.service.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SingleThreadPoolTest {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        /*executorService.schedule(()->{
            System.out.println("延迟1秒执行");
        },1, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println(executorService.isShutdown());*/

        /*executorService.scheduleAtFixedRate(()->{
            System.out.println("定时1秒执行");
        },0,1, TimeUnit.SECONDS);*/
    }
}
