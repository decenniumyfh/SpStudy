package com.yang.concurrent.service.Semaphore.pool.dbpoolList;

import lombok.SneakyThrows;

import java.util.Random;

public class BusinessService {

    public Long getSleep(){
        Random r = new Random();
        int max=3000;
        int min=10;
        return Long.valueOf(r.nextInt(max)%(max-min+1) + min);
    }

    @SneakyThrows
    public void work(){
        long time = getSleep();
        System.out.println("模拟业务执行开始:"+time+"----"+Thread.currentThread().getName());
        Thread.sleep(time);
        System.out.println("模拟业务执行结束:"+time+"----"+Thread.currentThread().getName());
    }
}
