package com.yang.concurrent.service.Semaphore.pool.dbpoollistAsync;

import com.yang.concurrent.service.Semaphore.pool.DBConn;
import com.yang.concurrent.service.Semaphore.pool.dbpoolList.BusinessService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ConnThreadTest {


    public static void main(String[] args){
        List<DBConn> list = new ArrayList<DBConn>();
        for(int i=0;i<1;i++){
            list.add(new DBConn("conn"+i,i));
        }
        Semaphore semaphore = new Semaphore(15,false);
        SyncConnPool<DBConn> pool = new SyncConnPool<DBConn>(list,semaphore);
        BusinessService businessService = new BusinessService();
        int i=0;
        for(;;){
            SyncConnThread thread = new SyncConnThread(pool,businessService);
            thread.setName("thread:"+i++);
            thread.start();
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
