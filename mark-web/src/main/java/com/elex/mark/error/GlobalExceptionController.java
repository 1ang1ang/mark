package com.elex.mark.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sun on 2017/7/5.
 */
@ControllerAdvice
//如果返回的为json数据或其它对象，添加该注解
@ResponseBody
public class GlobalExceptionController {
    private static final Logger logger = LoggerFactory
            .getLogger(GlobalExceptionController.class);


    //游戏逻辑异常处理类
    @ExceptionHandler(value=LogicException.class)
    public Object MethodArgumentNotValidHandler(HttpServletRequest request,
                                                LogicException exception) throws Exception
    {
        logger.error("found new exception!!!");
        //按需重新封装需要返回的错误信息

        List<String> invalidArguments = Arrays.asList(exception.getRetObj());
//        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
//        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
//        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
//            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
//            invalidArgument.setDefaultMessage(error.getDefaultMessage());
//            invalidArgument.setField(error.getField());
//            invalidArgument.setRejectedValue(error.getRejectedValue());
//            invalidArguments.add(invalidArgument);
//        }

        JsonResult resultMsg = new JsonResult(ResultCode.EXCEPTION, ResultCode.EXCEPTION.msg(), invalidArguments);
        return resultMsg;
    }

    //添加全局异常处理流程，根据需要设置需要处理的异常，本文以MethodArgumentNotValidException为例
    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    public Object MethodArgumentNotValidHandler(HttpServletRequest request,
                                                MethodArgumentNotValidException exception) throws Exception
    {
        logger.error("found new exception!!!");
        //按需重新封装需要返回的错误信息
//        List<String> invalidArguments = new ArrayList<>();
//        invalidArguments.add("1123344");
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setRejectedValue(error.getRejectedValue());
            invalidArguments.add(invalidArgument);
        }

        JsonResult resultMsg = new JsonResult(ResultCode.EXCEPTION, ResultCode.EXCEPTION.msg(), invalidArguments);
        return resultMsg;
    }
}
