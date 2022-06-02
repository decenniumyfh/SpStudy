package com.yang.concurrent.service.threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoollExecutorTest {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"---"+System.currentTimeMillis());
            }
        };

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,10, 10,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
        threadPoolExecutor.submit(runnable);
    }
}
