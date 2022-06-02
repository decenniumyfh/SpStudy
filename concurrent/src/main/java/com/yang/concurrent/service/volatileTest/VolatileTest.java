package com.yang.concurrent.service.volatileTest;

import java.util.concurrent.TimeUnit;

public class VolatileTest {

    public static void main(String[] args) {
        VolatileDemo demo = new VolatileDemo();
        for(int i=0;i<30;i++){
            VolatileThread thread = new VolatileThread(demo);
            thread.start();

        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(demo.getCountSafe());
        System.out.println(demo.getCountUnSafe());
        System.out.println(demo.getCountAutomic().get());

    }
}
