package com.elex.mark.error;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 游戏异常
 */
public class LogicException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(LogicException.class);

    private ResultCode exceptionCode;
    private String[] retObj;

    public LogicException() {
        super("Unknown Error");
        exceptionCode = ResultCode.EXCEPTION;
    }

    public LogicException(ResultCode exceptionCode) {
        super("Unknown Error");
        this.exceptionCode = exceptionCode;
    }

    public LogicException(ResultCode exceptionCode, String exceptionDesc) {
        super(exceptionDesc);
        this.exceptionCode = exceptionCode;
    }

    public LogicException(ResultCode exceptionCode, String exceptionDesc, String... retObj) {
        super(exceptionDesc);
        logger.error(exceptionDesc);
        this.retObj = retObj;
        this.exceptionCode = exceptionCode;
    }

    /**
     * @return the exceptionCode
     */
    public ResultCode getExceptionCode() {
        return exceptionCode;
    }

    /**
     * @return the retObj
     */
    public String[] getRetObj() {
        return retObj;
    }

    public void setRetObj(String[] retObj) {
        this.retObj = retObj;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
//        String obj = new SFSObject();
        obj.put("errorCode", exceptionCode.val());
        obj.put("errorMsg", getMessage());
        if (retObj != null && retObj.length > 0) {
            obj.put("errorData", retObj);
        }
        return obj.toJSONString();
    }
}
