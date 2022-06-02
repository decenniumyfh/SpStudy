package com.yang.concurrent.service.countDownLatch.runner;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

public class WaitThread  extends Thread{

    private CountDownLatch countDownLatch;

    public WaitThread(CountDownLatch countDownLatch,String name){
        this.countDownLatch = countDownLatch;
        this.setName(name);
    }
    @SneakyThrows
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"---开始");
        //使当前线程进入同步队列进行等待，直到countDownLatch的值被减到0或者当前线程被中断，当前线程就会被唤醒。
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"----所有runner线程结束");

    }
}