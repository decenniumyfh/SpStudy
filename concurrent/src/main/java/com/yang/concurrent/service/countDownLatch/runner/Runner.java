package com.yang.concurrent.service.countDownLatch.runner;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

public class Runner extends Thread{

    private CountDownLatch countDownLatch;
    public Runner(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }
    @SneakyThrows
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"---begin");
        Thread.sleep((int)Math.random()*5000);
        System.out.println(Thread.currentThread().getName()+"---结束");
        //执行一次,计数器减1
        countDownLatch.countDown();

    }
}
