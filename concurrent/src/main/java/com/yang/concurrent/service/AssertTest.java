package com.yang.concurrent.service;

public class AssertTest {

    public static void main(String[] args) {
        assert true;
        System.out.println("断言1没有问题");
        assert false;
        System.out.println("断言2有问题");
    }
}
