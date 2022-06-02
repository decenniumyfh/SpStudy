package com.yang.concurrent.service.countDownLatch;

public class CountDownLatchServiceThread extends Thread{

    private CountDownLatchService countDownLatchService;

    public CountDownLatchServiceThread(CountDownLatchService countDownLatchService){
        this.countDownLatchService = countDownLatchService;
    }

    @Override
    public void run() {
        countDownLatchService.testMethodA();
    }
}
