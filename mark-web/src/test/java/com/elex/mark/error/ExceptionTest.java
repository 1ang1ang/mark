package com.elex.mark.error;


import org.junit.Test;

/**
 * Created by sun on 2017/7/5.
 */
public class ExceptionTest {

    @Test
    public void ExceptionTest() throws LogicException {
        throw new LogicException(ResultCode.EXCEPTION, "this is test exception", "param1", "param2");
    }
}
