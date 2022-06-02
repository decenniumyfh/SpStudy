package com.yang.concurrent.service.clh;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private static final class Sync extends AbstractQueuedSynchronizer {
        Sync(int count){
            if(count<=0){
                throw new IllegalArgumentException("count is error");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for(;;){
                int current  = getState();
                int newCount = current-arg;
                if(newCount>0 || compareAndSetState(current,newCount)){
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for(;;){
                int current = getState();
                int newCount = current+arg;
                if(compareAndSetState(current,newCount)){
                    return true;
                }
            }
        }
    }

}
