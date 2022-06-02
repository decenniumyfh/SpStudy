package com.yang.concurrent.service.Semaphore.pool.dbpoolList;

import com.yang.concurrent.service.Semaphore.pool.DBConn;

import java.util.List;
import java.util.Random;

public class ConnThread extends Thread{

    private ConnPool<DBConn> pool = null;

    private BusinessService businessService;

    public ConnThread(ConnPool pool,BusinessService businessService){
        this.pool = pool;
        this.businessService = businessService;
    }


    @Override
    public void run() {
        DBConn conn = null;
        //System.out.println("线程启动..."+Thread.currentThread().getName());
        conn = pool.acquire();
        if(conn==null){
            System.out.println("获取对象池数据失败.......");
        }else{
            businessService.work();
        }

        pool.release(conn);


    }
}
