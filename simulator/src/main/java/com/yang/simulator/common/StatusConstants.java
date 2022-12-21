package com.yang.simulator.common;

import lombok.Getter;

@Getter
public final class StatusConstants {
    /**
     * 返回成功.
     */
    public static final int    SUCCESS                                = 200;
    /**
     * 返回成功.
     */
    public static final String SUCCESS_MSG                            = "操作成功";

    /**
     * 服务异常.
     */
    public static final int    SERVICE_FAIL                = 400;
    /**
     * 服务端业务处理内部异常.
     */
    public static final String SERVICE_FAIL_MSG            = "服务异常";




}
