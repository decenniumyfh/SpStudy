package com.yang.concurrent.service;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class ThreadJoin {

    @SneakyThrows
    public static void main(String[] args) {

        Thread preThread = Thread.currentThread();

        for(int i=0;i<5;i++){
            DemoThread thread = new DemoThread(preThread,"thread"+i);
            thread.start();
            preThread = thread;
        }
        //TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println(Thread.currentThread().getName() +"....working");
    }

    static class DemoThread extends Thread{

        Thread preThread;
        DemoThread(Thread preThread,String name){
            this.preThread = preThread;
            this.setName(name);
        }
        @SneakyThrows
        @Override
        public void run() {
            //用于判断加入的线程是否执行完成，如果加入的线程生命周期结束，则继续向下执行，否则阻塞
            preThread.join();
            //TimeUnit.MILLISECONDS.sleep(1000);

            System.out.println(Thread.currentThread().getName() +"....working");

        }
    }
}
