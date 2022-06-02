package com.yang.concurrent.service.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class CyclicBarrierTest {

    public static void main(String[] args) {
        int size =10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(size);
        IntStream.range(0,size).forEach(t->{
            CyclicBarrierThread thread = new CyclicBarrierThread(cyclicBarrier,"thread"+t);
            thread.start();
        });
    }
}
