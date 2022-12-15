package com.yang.concurrent.service.generic;

public class Person<E> {
    E e;

    public Person(E e){
        this.e = e;
    }

    public E getE() {
        return e;
    }

    public void showType(){
        System.out.println(e.getClass());
    }

}
