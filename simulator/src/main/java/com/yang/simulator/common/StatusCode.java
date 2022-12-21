package com.yang.simulator.common;

import lombok.Getter;

@Getter
public enum StatusCode {

    /**
     * 错误码规范：
     * 1、统一错误，平台错误，定义为isp，错误码从200起编制；
     * 2、业务错误，子系统错误，定义为isv，错误码从500起编制；
     * 3、isv必须指定isp父错误编码;
     */

    /**
     * 返回成功.
     */
    SUCCESS(StatusConstants.SUCCESS,  StatusConstants.SUCCESS_MSG),
    /**
     * 服务异常.
     */
    SERVICE_FAIL(StatusConstants.SERVICE_FAIL,
            StatusConstants.SERVICE_FAIL_MSG);


    private int code;
    /**
     * 英文错误信息.
     */
    private String msg;
    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
