package com.yang.concurrent.service.threadpool;

public class RunnableTest implements Runnable{

    private String name;
    public RunnableTest(String name){
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"---"+System.currentTimeMillis()+"----"+name);
    }
}
