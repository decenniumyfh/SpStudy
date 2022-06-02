package com.yang.concurrent.service.Semaphore.pool.listpool;

import java.util.Random;

public class ListPoolThread extends Thread{

    private ListPool pool;
    public ListPoolThread(ListPool pool){
        this.pool = pool;
    }

    public Long getSleep(){
        Random r = new Random();
        int max=300;
        int min=100;
        return Long.valueOf(r.nextInt(max)%(max-min+1) + min);
    }

    @Override
    public void run() {
        try {
            String str = pool.acquire();
            System.out.println("获取字符串"+str);
            Thread.sleep(getSleep());
            pool.release(str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
