package com.vianstats.media.convert.advice;

import com.vianstats.media.convert.dto.CommonResult;
import com.vianstats.media.convert.enums.CommonResultEnum;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author liangwj
 * @date 2020-06-08
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * 参数异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 返回错误信息
        // 对结果进行封装
        return CommonResult.ofFail(CommonResultEnum.FAIL.getCode(), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 业务错误异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(VianException.class)
    public CommonResult VianExceptionHandler(VianException e) {
        return CommonResult.ofFail(CommonResultEnum.BUSINESS_ERROR.getCode(),e.getErrMsg());
    }
}
