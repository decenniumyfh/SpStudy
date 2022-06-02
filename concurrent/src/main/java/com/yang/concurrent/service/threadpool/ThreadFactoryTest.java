package com.yang.concurrent.service.threadpool;

import java.util.concurrent.ThreadFactory;

public class ThreadFactoryTest implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        return thread;
    }
}
