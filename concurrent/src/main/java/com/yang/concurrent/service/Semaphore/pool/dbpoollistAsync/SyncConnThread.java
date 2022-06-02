package com.yang.concurrent.service.Semaphore.pool.dbpoollistAsync;

import com.yang.concurrent.service.Semaphore.pool.DBConn;
import com.yang.concurrent.service.Semaphore.pool.dbpoolList.BusinessService;

public class SyncConnThread extends Thread{

    private SyncConnPool<DBConn> pool = null;

    private BusinessService businessService;

    public SyncConnThread(SyncConnPool pool, BusinessService businessService){
        this.pool = pool;
        this.businessService = businessService;
    }


    @Override
    public void run() {
        DBConn conn = null;
        //System.out.println("线程启动..."+Thread.currentThread().getName());
        conn = pool.acquire();
        businessService.work();
        pool.release(conn);


    }
}
