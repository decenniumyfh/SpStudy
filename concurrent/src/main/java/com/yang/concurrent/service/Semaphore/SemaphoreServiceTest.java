package com.yang.concurrent.service.Semaphore;

public class SemaphoreServiceTest {


    public  static void testSemaphore(){
        SemaphoreService semaphoreService =new SemaphoreService();

        ThreadA a = new ThreadA(semaphoreService);
        a.setName("a");

        ThreadA b = new ThreadA(semaphoreService);
        b.setName("b");

        ThreadA c = new ThreadA(semaphoreService);
        c.setName("c");

        a.start();
        b.start();
        c.start();


    }


    public static void testRunnable(){
        RunnableTest test = new RunnableTest();
        new Thread(test).start();
        new Thread(test).start();
    }

    public static void main(String[] args){
        testSemaphore();
         //boolean[] used = new boolean[10];
        //System.out.println(used.length);
    }

}
