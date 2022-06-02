package com.yang.concurrent.service.Semaphore.pool.dbpoolList;

import com.yang.concurrent.service.Semaphore.pool.DBConn;
import com.yang.concurrent.service.Semaphore.pool.dbpoollistAsync.SyncConnPool;
import com.yang.concurrent.service.Semaphore.pool.dbpoollistAsync.SyncConnThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ConnThreadTest {


    public static void main(String[] args){
        List<DBConn> list = new ArrayList<DBConn>();
        for(int i=0;i<10;i++){
            list.add(new DBConn("conn"+i,i));
        }
        Semaphore semaphore = new Semaphore(15,false);
        ConnPool<DBConn> pool = new ConnPool<DBConn>(list,semaphore);
        BusinessService businessService = new BusinessService();
        int i=0;
        for(;;){
            ConnThread thread = new ConnThread(pool,businessService);
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
