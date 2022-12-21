package com.yang.simulator.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result <T> implements Serializable {

    private int code;
    private String msg;
    private T data;
    @JsonIgnore
    private StatusCode status;

    public Result(){}

    public Result(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(int code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(StatusCode status){
        this.code = status.getCode();
        this.msg = status.getMsg();
    }
    public Result(StatusCode status,T data){
        this.code = status.getCode();
        this.msg = status.getMsg();
        this.data = data;
    }



}
