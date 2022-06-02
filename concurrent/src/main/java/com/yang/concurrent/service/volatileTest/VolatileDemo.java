package com.yang.concurrent.service.volatileTest;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {

    private volatile int countSafe = 0;
    private volatile int countUnSafe = 0;
    private AtomicInteger countAutomic = new AtomicInteger(0);
    //1、volatile 无法保证++操作的线程安全，因为一次++操作，实际是发生了一次读和一次写。并且他依赖于旧值进行更新
    //2、如果飞要如此使用，可以增加关键字synchronized。 利用volatile保证读取操作的可见性；利用synchronized保证复合操作的原子性
    //3、使用原子类实现++，–，+delta等线程安全操作
    public synchronized  void getIncrementSafe(){
        for(int i=0;i<1000;i++){
            countSafe++;
        }
    }
    public void getIncrementUnSafe(){
        for(int i=0;i<1000;i++){
            countUnSafe++;
        }
    }
    public void getIncrementAtomic(){
        for(int i=0;i<1000;i++){
            countAutomic.getAndIncrement();
            //countAutomic.getAndAdd(1);
        }
    }

    public AtomicInteger getCountAutomic(){
        return this.countAutomic;
    }

    public int getCountSafe(){
        return this.countSafe;
    }
    public int getCountUnSafe(){
        return this.countUnSafe;
    }


}
