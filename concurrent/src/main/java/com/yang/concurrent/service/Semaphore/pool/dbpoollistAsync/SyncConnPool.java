package com.yang.concurrent.service.Semaphore.pool.dbpoollistAsync;



import java.util.List;
import java.util.concurrent.Semaphore;
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
public class SyncConnPool<DBConn> {

    private Semaphore semaphore;

    private List<DBConn> list;


    private final static ReentrantLock reentrantLock = new ReentrantLock();
    private final static Condition condition = reentrantLock.newCondition();

    public SyncConnPool(List<DBConn> list, Semaphore semaphore){
        this.list = list;
        this.semaphore = semaphore;
    }


    public synchronized DBConn getData(){
        DBConn t = null;
        Long date = System.currentTimeMillis();
        while (list.size()==0){
            System.out.println("池中无数据,等待..."+Thread.currentThread().getName()+"---date:"+date);
            return acquire();
        }
        t = list.remove(0);
        return t;
    }
    public DBConn acquire(){
        DBConn t = null;
        try {
            semaphore.acquire();
            Long date = System.currentTimeMillis();
            System.out.println("成功获取permits:"+Thread.currentThread().getName()+"---date:"+date);
            t= getData();
            System.out.println("池对象获取成功:"+t+"---"+Thread.currentThread().getName()+"---date:"+date);

        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return t;
    }


    public void release(DBConn t){
        list.add(t);
        semaphore.release();
        System.out.println("");
        System.out.println("数据释放:"+t+"---"+System.currentTimeMillis());
        System.out.println("");



    }



}
