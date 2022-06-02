package com.yang.concurrent.service.countDownLatch.runner;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RunnerTest {

    public static void main(String[] args) throws InterruptedException {
        int countSize = 10;
        CountDownLatch countDownLatch = new CountDownLatch(countSize);

        WaitThread waitThread = new WaitThread(countDownLatch,"等待线程");
        waitThread.start();
        IntStream.range(0,countSize).forEach(t->{
            Runner runner = new Runner(countDownLatch);
            runner.start();
        });



    }
}
