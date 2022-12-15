package com.yang.concurrent.service.generic;

public class GenericTest {

    public static void main(String[] args) {
        Person<String> p = new Person<>("test");
        System.out.println(p.getE());
        p.showType();

        Person<Integer> i = new Person<>(22);
        System.out.println(i.getE());
        i.showType();
    }
}
