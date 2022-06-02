package com.yang.concurrent.service.Semaphore.pool.dbpoolArr;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/****************************************************************
 * Copyright © 2020,yangfh,634607608@qq.com
 * All Rights Reserved.
 *
 * 文件名称： com.yang.concurrent.service.Semaphore.pool.dbpool.SemaphorePool
 * 摘    要：使用Semaphore实现通用可复用对象池定义
 *
 * 初始版本：1.0.0
 * 原 作 者：yangfh
 * 完成日期：2022/5/7 1:39
 *
 * 当前版本：1.0.0
 * 作    者：yangfh
 * 完成日期：2022/5/7 1:39
 *****************************************************************/
public class SemaphorePool<T> {
    protected  int MAX_AVAILABLE;
    protected  Semaphore semaphore;

    protected T[] items;
    protected boolean[] used;

    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();

    /**
     * 构造函数传入资源信息
     * @param items
     * @param fair
     */
    public SemaphorePool(T[] items,Integer permits,Boolean fair){
        this.items = items;
        this.MAX_AVAILABLE = items.length;
        //资源和信号量为1：1关系
        //fair 公平信号量，
        // true:线程的启动顺序和调用semaphore.acquire()的顺序相关
        // false：线程的启动顺序和调用semaphore.acquire()的顺序无关,也就是线程启动了，不一定能获取permits
        this.used = new boolean[this.MAX_AVAILABLE];
        this.semaphore = new Semaphore(permits,fair);
    }

    /**
     * 获取资源
     * @return
     * @throws InterruptedException
     */
    public  T acquire() throws InterruptedException {
        T item = null;
        try {

            semaphore.acquire();
            //System.out.println("");
            Long date = System.currentTimeMillis();
            System.out.println("成功获取permits:"+Thread.currentThread().getName()+"---date:"+date);
            reentrantLock.lock();

            while ((item = getNextAvailableItem())==null){
                System.out.println("池中无数据,等待..."+Thread.currentThread().getName()+"---date:"+date);
                condition.await();
            }

            reentrantLock.unlock();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return item;
    }

    /**
     * 释放资源
     * @param item
     */
    public void release(T item){

        reentrantLock.lock();
        markAsUnused(item);
        condition.signalAll();
        reentrantLock.unlock();
        semaphore.release();

    }


    /**
     * 标记资源已使用
     * @return
     */
    protected T getNextAvailableItem() throws InterruptedException {
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            if (!used[i]) {
                used[i] = true;
                return items[i];
            }
        }
        return null;
    }

    /**
     * 标记资源未使用
     * @param item
     * @return
     */
    protected boolean markAsUnused(T item) {
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            if (item == items[i]) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }
}
