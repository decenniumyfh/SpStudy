package com.yang.concurrent.service.Semaphore.pool.listpool;

public class ListPoolTest {


    public static void main(String[] args){
        ListPool pool = new ListPool();

        for(int i=0;i<100;i++){
            ListPoolThread thread = new ListPoolThread(pool);
            thread.start();
        }

    }
}
