package com.yang.concurrent.service.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkStealingPoolTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newWorkStealingPool(2);
        System.out.println(Runtime.getRuntime().availableProcessors());
        for(int i=0;i<100;i++){
            executorService.submit(new RunnableTest(""+i));
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();


    }
}
