package com.yang.concurrent.service.threadpool;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MyCallable implements Callable<String> {

    private String name;

    public MyCallable(String name){
        this.name = name;
    }

    public Long getSleep(){
        Random r = new Random();
        int max=3000;
        int min=10;
        return Long.valueOf(r.nextInt(max)%(max-min+1) + min);
    }
    @Override
    public String call() throws Exception {
        TimeUnit.MILLISECONDS.sleep(getSleep());
        return Thread.currentThread().getName()+"---return:"+name;
    }
}
