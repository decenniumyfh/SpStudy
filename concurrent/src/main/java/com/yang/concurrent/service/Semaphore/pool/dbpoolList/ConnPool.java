package com.yang.concurrent.service.Semaphore.pool.dbpoolList;


import lombok.Synchronized;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/****************************************************************
 *
 * 文件名称： com.yang.concurrent.service.Semaphore.pool.dbpoolList.ConnPool
 * 摘    要：https://blog.csdn.net/suifeng629/article/details/106159163
 *
 * 初始版本：1.0.0
 * 原 作 者：yangfh
 * QQ: 634607608
 * 完成日期：2022/5/9 0:04
 *
 * 当前版本：1.0.0
 * 作    者：yangfh
 * 完成日期：2022/5/9 0:04
 *****************************************************************/
public class ConnPool<T> {

    private Semaphore semaphore;

    private List<T> list;


    private final static ReentrantLock reentrantLock = new ReentrantLock();
    private final static Condition condition = reentrantLock.newCondition();

    public ConnPool(List<T> list,Semaphore semaphore){
        this.list = list;
        this.semaphore = semaphore;
    }

    public Long getSleep(){
        Random r = new Random();
        int max=3000;
        int min=10;
        return Long.valueOf(r.nextInt(max)%(max-min+1) + min);
    }
    public T acquire(){
        T t = null;
        boolean lockFlag = false;
        try {

            semaphore.acquire();
            Long date = System.currentTimeMillis();
            System.out.println("成功获取permits:"+Thread.currentThread().getName()+"---date:"+date);
            lockFlag = reentrantLock.tryLock(1L, TimeUnit.MILLISECONDS);
            if(lockFlag){
                Thread.sleep(getSleep());
                while (list.size()==0){
                    System.out.println("池中无数据,等待..."+Thread.currentThread().getName()+"---date:"+date);
                    condition.await();
                }
                t = list.remove(0);
                System.out.println("池对象获取成功:"+t+"---"+Thread.currentThread().getName()+"---date:"+date);
            }else{
                System.out.println("获取锁对象失败，未获取数据");
            }


        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            if(lockFlag){
                System.out.println("释放锁..."+Thread.currentThread().getName());
                reentrantLock.unlock();

            }
        }
        return t;
    }


    public   void release(T t){
        reentrantLock.lock();
        list.add(t);
        condition.signalAll();
        reentrantLock.unlock();
        semaphore.release();
        System.out.println("");
        System.out.println("数据释放:"+t+"---"+System.currentTimeMillis());
        System.out.println("");



    }



}
