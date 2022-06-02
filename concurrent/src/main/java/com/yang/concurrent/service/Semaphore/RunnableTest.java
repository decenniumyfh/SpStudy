package com.yang.concurrent.service.Semaphore;

public class RunnableTest implements Runnable{
    @Override
    public void run() {
        System.out.println("实现Runnable接口，调用run()方法--"+System.currentTimeMillis());
    }
}
