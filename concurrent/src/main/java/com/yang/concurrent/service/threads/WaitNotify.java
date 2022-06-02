package com.yang.concurrent.service.threads;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
/****************************************************************
 *
 * 文件名称： com.yang.concurrent.service.threads.WaitNotify
 * 摘    要：对象的等待通知模式
 *
 * 初始版本：1.0.0
 * 原 作 者：yangfh
 * QQ: 634607608
 * 完成日期：2022/5/19 9:50
 *
 * 当前版本：1.0.0
 * 作    者：yangfh
 * 完成日期：2022/5/19 9:50
 *****************************************************************/
public class WaitNotify {
    static boolean waiting = true;
    static Object lock = new Object();

    static class WaiterThread extends Thread{

        @SneakyThrows
        @Override
        public void run() {
            synchronized (lock){
                while(waiting){
                    System.out.println(Thread.currentThread().getName() +"... is waiting ..."+System.currentTimeMillis());
                    //wait()方法将导致当前线程进入block状态,释放CPU资源.
                    lock.wait();
                    System.out.println("这句代码被加锁，wait ，直到释放锁才会继续执行");
                }
                System.out.println(Thread.currentThread().getName() +"... is working ..."+System.currentTimeMillis());
            }
        }
    }

    static class NotifyRunnable implements Runnable{
        @SneakyThrows
        @Override
        public void run() {
            synchronized (lock){
                System.out.println(Thread.currentThread().getName() +"... is notify ..."+System.currentTimeMillis());
                lock.notifyAll();
                System.out.println("锁释放，可执行");
                waiting = false;
            }
            TimeUnit.MILLISECONDS.sleep(1000);

            synchronized (lock){
                System.out.println(Thread.currentThread().getName() +"再次加锁... is notify ..."+System.currentTimeMillis());
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        WaiterThread waitThread = new WaiterThread();
        waitThread.setName("waiter");
        waitThread.start();
        TimeUnit.MILLISECONDS.sleep(500);
        Thread notifyThread = new Thread(new NotifyRunnable(), "NotifyThread");
        notifyThread.setName("notify");
        notifyThread.start();
    }

}
