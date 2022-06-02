package com.yang.concurrent.service.cyclicBarrier;

import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierThread extends Thread{

    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierThread(CyclicBarrier cyclicBarrier,String name){
        this.cyclicBarrier = cyclicBarrier;
        this.setName(name);
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println(this.getName()+"---begin");
        Thread.sleep((int)Math.random()*5000);
        cyclicBarrier.await();
        System.out.println(this.getName()+"---end");

        super.run();
    }
}
