package com.yang.concurrent.service.Semaphore;

import com.yang.concurrent.service.Semaphore.SemaphoreService;

public class ThreadA extends Thread{

    private SemaphoreService service;

    public ThreadA(SemaphoreService service){
        this.service = service;
    }

    @Override
    public void run() {
        //super.run();
        service.testMethod();
    }
}
