package com.yang.simulator.common;

import org.slf4j.Logger;

public class ResultUtil {


    public static Result getSuccess(){
        Result result = new Result(StatusCode.SUCCESS);
        return  result;
    }

    public static Result getFail(){
        Result result = new Result(StatusCode.SERVICE_FAIL);
        return  result;
    }

    public static Result getResult(int code,String msg){
        Result result = new Result(code,msg);
        return  result;
    }
    public static Result getResult(StatusCode statusCode){
        Result result = new Result(statusCode);
        return  result;
    }

    public static Result getResult(int code){
        String msg = null;
        if(code==StatusConstants.SUCCESS){
            msg = StatusConstants.SUCCESS_MSG;
        }else{
            msg = StatusConstants.SERVICE_FAIL_MSG;
        }
        Result result = new Result(code,msg);
        return  result;
    }

    public static Result getResult(int code,String msg,Object data){
        Result result = new Result(code,msg,data);
        return  result;
    }

    public static Result getResult(StatusCode statusCode,Object data){
        Result result = new Result(statusCode,data);
        return  result;
    }

    public static Result getSuccess(Object data){
        Result result = new Result(StatusCode.SUCCESS,data);
        return  result;
    }

    public static Result getResult(Exception e, Logger logger) {
        Result result = new Result(StatusCode.SERVICE_FAIL);
        result.setMsg(StatusCode.SERVICE_FAIL.getMsg()+":"+e.getMessage()+","+e);
        logger.error("异常信息：",e);
        return result;
    }


}
