package com.yang.concurrent.service;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Profiler {

     static final ThreadLocal<Long> threadLocal = ThreadLocal.withInitial(() -> {
        //1、初始化赋值，如果没调用set方法，第一次进行get()请求前，会进行一次初始化，
        long time = System.currentTimeMillis();
        System.out.println("init:"+time);
        return time;
    });

    public static void begin(){
        long time = System.currentTimeMillis();
        System.out.println("beign:"+time);
        //2、将注释去掉，threadLocal手工初始化后，将不会再调用withInitial方法进行初始化。
        //threadLocal.set(System.currentTimeMillis());
    }

    public static void end(){
        long time = System.currentTimeMillis();
        System.out.println("end:"+time);
        long begin = threadLocal.get();
        System.out.println("end...begin:"+begin);
        System.out.println(time - threadLocal.get());
    }

    @SneakyThrows
    public static void main(String[] args) {
        Profiler.begin();
        TimeUnit.MILLISECONDS.sleep(1500);
        Profiler.end();
    }

}
