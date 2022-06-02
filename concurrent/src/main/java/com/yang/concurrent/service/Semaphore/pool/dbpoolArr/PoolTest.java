package com.yang.concurrent.service.Semaphore.pool.dbpoolArr;

import com.yang.concurrent.service.Semaphore.pool.DBConn;

/****************************************************************
 * Copyright © 2020,yangfh,634607608@qq.com
 * All Rights Reserved.
 *
 * 文件名称： com.yang.concurrent.service.Semaphore.pool.dbpool.PoolTest
 * 摘    要：[简要描述本文件的内容]
 *
 * 初始版本：1.0.0
 * 原 作 者：yangfh
 * 完成日期：2022/5/7 1:39
 *
 * 当前版本：1.0.0
 * 作    者：yangfh
 * 完成日期：2022/5/7 1:39
 *****************************************************************/
public class PoolTest {


    public static void main(String[] args) throws InterruptedException {

        //可使用对象数量
        int poolSize = 3;
        //线程数量
        int threadNum = 15;

        int permits = 10;

        //构建资源总数
        DBConn[] connArr = new DBConn[poolSize];
        for(int i=0;i<poolSize;i++){
            connArr[i] = new DBConn("conn_"+ i,i);
        }



        SemaphorePool<DBConn> pool = new SemaphorePool<>(connArr,permits,false);
        //使用
        for(int i=0;i<threadNum;i++){
            PoolThread thread = new PoolThread(pool,true);
            thread.setName(Integer.toString(i));
            thread.start();

        }

    }
}
