package com.yang.concurrent.service.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchService {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void testMethodA(){
        try {
            System.out.println("testMethodA....1");
            countDownLatch.await();
            System.out.println("testMethodA....2");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void testMethodB(){
        System.out.println("testMethodB....1");
        countDownLatch.countDown();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("testMethodB....2");
    }

}
