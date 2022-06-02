package com.yang.concurrent.service.Semaphore;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;


public class SemaphoreService {

    private Semaphore semaphore = new Semaphore(2);


    public void testMethod(){
        try {
            semaphore.acquire();

            System.out.println(Thread.currentThread().getName()+"---begin time:"+System.currentTimeMillis());
            //System.out.println("availablePermits:"+semaphore.availablePermits());
           // Thread.sleep(500);
            semaphore.release();
            System.out.println(Thread.currentThread().getName()+"---end time:"+System.currentTimeMillis());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
