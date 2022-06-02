package com.yang.concurrent.service.Semaphore.pool;

import lombok.Data;
/****************************************************************
 * Copyright © 2020,yangfh,634607608@qq.com
 * All Rights Reserved.
 *
 * 文件名称： com.yang.concurrent.service.Semaphore.pool.dbpool.DBConn
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
@Data
public class DBConn {

    private String name;
    private Integer idx;

    public DBConn(String name,Integer idx){
        this.name = name;
        this.idx = idx;
    }


    @Override
    public String toString() {
        return "DBConn{" +
                "name='" + name + '\'' +
                ", idx=" + idx +
                '}';
    }
}
