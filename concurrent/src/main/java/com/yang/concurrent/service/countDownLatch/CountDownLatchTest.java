package com.yang.concurrent.service.countDownLatch;

import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatchService countDownLatchService = new CountDownLatchService();
        CountDownLatchServiceThread thread = new CountDownLatchServiceThread(countDownLatchService);
        thread.start();
        TimeUnit.SECONDS.sleep(3);
        countDownLatchService.testMethodB();
    }
}
