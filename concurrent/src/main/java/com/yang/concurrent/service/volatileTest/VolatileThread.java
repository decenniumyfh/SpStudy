package com.yang.concurrent.service.volatileTest;

public class VolatileThread extends Thread{

    private VolatileDemo demo;
    public VolatileThread(VolatileDemo demo){
        this.demo = demo;
    }

    @Override
    public void run() {
        demo.getIncrementSafe();
        demo.getIncrementUnSafe();
        demo.getIncrementAtomic();
    }
}
