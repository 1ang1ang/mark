package com.elex.mark.enums;

/**
 * Created by sun on 2017/7/10.
 */
public enum  LogicErrorCode {
    NO_AUTHORITY(1, "没有权限"),
    ;

    private  int code;
    private String msg;

    LogicErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
