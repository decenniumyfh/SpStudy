package com.yang.concurrent.service.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadFactoryTest factoryTest = new ThreadFactoryTest();

        ExecutorService executorService = Executors.newFixedThreadPool(3,factoryTest);
        List<Future> futureList = new ArrayList<Future>();
        IntStream.range(0,10).forEach(t->{
            //execute() 无放回值
            //submit 有返回值
            Future future = executorService.submit(new RunnableTest("runnable"+t));
            futureList.add(future);
            System.out.println("刚提交，不一定执行:"+future.isDone());
        });

        TimeUnit.SECONDS.sleep(5L);
        System.out.println("休眠5秒");
        futureList.forEach(t->{
            System.out.println("执行完成:"+t.isDone());
        });

        executorService.shutdown();
    }
}
