package com.yang.concurrent.service.Semaphore.pool.dbpoolArr;

import com.yang.concurrent.service.Semaphore.pool.DBConn;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/****************************************************************
 * Copyright © 2020,yangfh,634607608@qq.com
 * All Rights Reserved.
 *
 * 文件名称： com.yang.concurrent.service.Semaphore.pool.dbpool.PoolThread
 * 摘    要：[简要描述本文件的内容]
 *
 * 初始版本：1.0.0
 * 原 作 者：yangfh
 * 完成日期：2022/5/7 1:39
 *
 * 当前版本：1.0.0
 * 作    者：yangfh
 * 完成日期：2022/5/7 1:39
 *****************************************************************/
public class PoolThread extends Thread{

    private SemaphorePool<DBConn> pool;

    //使用ReentrantLock 实现控制多线程执行业务的顺序性
    //当加锁的时候，任务的执行是同步的，线程阻塞，一个线程执行完才能执行下一个线程
    //不加锁的时候，线程的执行是同步的，多个线程可同时执行相同的任务
    private static ReentrantLock lock;
    static{
        lock = new ReentrantLock();
    }
    //设置线程是否多处理，true:多处理，false:单处理
    private Boolean multi;

    public PoolThread(SemaphorePool<DBConn> pool, Boolean multi){
        this.pool = pool;
        this.multi = multi;

    }

    public PoolThread(SemaphorePool<DBConn> pool){
        this.pool = pool;
        this.multi = true;

    }

    public Long getSleep(){
        Random r = new Random();
        int max=3000;
        int min=10;
        return Long.valueOf(r.nextInt(max)%(max-min+1) + min);
    }
    @Override
    public void run() {
        try {
            DBConn item = pool.acquire();


            if(!multi){
                lock.lock();
            }
            //模拟线程使用
            Long l = getSleep();
            System.out.println(l);
           // Thread.sleep(getSleep());
            for(int i=0;i<3;i++){
                System.out.println("业务处理---线程名:"+Thread.currentThread().getName()+"--打印:"+i);
            }
            if(!multi){
                lock.unlock();
            }
            pool.release(item);


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
