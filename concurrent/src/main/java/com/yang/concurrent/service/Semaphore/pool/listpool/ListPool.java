package com.yang.concurrent.service.Semaphore.pool.listpool;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ListPool {

    private int poolSize = 2;
    private int permits = 3;
    private ArrayList<String> list = new ArrayList<>();
    private Semaphore semaphore = new Semaphore(permits);
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();

    public ListPool(){
        for(int i=0;i<poolSize;i++){
            list.add("数据"+i);
        }
    }

    public String acquire()  {
        String str = null;
        try {
            semaphore.acquire();
            reentrantLock.lock();
            while (list.size()==0){
                System.out.println("池中无数据,等待..."+System.currentTimeMillis());
                condition.await();
            }
            str = list.remove(0);
            reentrantLock.unlock();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return str;

    }

    public void release(String str){
        reentrantLock.lock();
        list.add(str);
        condition.signalAll();
        reentrantLock.unlock();
        semaphore.release();
        System.out.println("数据释放:"+str+"---"+System.currentTimeMillis());
    }

}
