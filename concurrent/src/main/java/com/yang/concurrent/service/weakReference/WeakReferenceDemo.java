package com.yang.concurrent.service.weakReference;

import java.lang.ref.WeakReference;

public class WeakReferenceDemo {

    public static WeakReference<String> weakReference;

    public static void main(String[] args) {

        test1();
        //1、方法执行结束，方法中的局部变量被标记为可回收,等待gc回收
        //2、未进行gc，弱引用对象扔存在
        System.out.println("未进行gc时，只有弱引用指向value内存区域：" + weakReference.get());

        //3、强制gc回收，强引用被清理，关联弱引用指向空值
        System.gc();

        //此时输出都为nuill
        System.out.println("进行gc时，只有弱引用指向value内存区域：" + weakReference.get());

    }

    public static void test1() {
        String hello = new String("value");
        weakReference = new WeakReference<>(hello);
        System.gc();
        //此时gc不会回收弱引用，因为字符串"value"仍然被hello对象强引用
        System.out.println("进行gc时，强引用与弱引用同时指向value内存区域：" + weakReference.get());
    }
}
