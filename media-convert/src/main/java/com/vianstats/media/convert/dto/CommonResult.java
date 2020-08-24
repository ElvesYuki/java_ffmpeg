package com.vianstats.media.convert.dto;

import com.vianstats.media.convert.enums.CommonResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 通用返回结果
 * @param <T>
 */
@ApiModel(value="通用返回结果", description="")
public class CommonResult<T> implements Serializable {
    /**
     * 是否成功
     */
    @ApiModelProperty(value = "是否成功")
    private boolean isSuccess;


    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private long ts = System.currentTimeMillis();
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private String code;

    /**
     * 提示语，返回信息
     */
    @ApiModelProperty(value = "提示语")
    private String msg;

    /**
     * 返回数据集
     */
    @ApiModelProperty(value = "结果集")
    private T data;

    public static CommonResult ofSuccess() {
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccess(true);
        return commonResult;
    }

    public static CommonResult ofSuccess(String code, String msg) {
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccess(true);
        commonResult.setCode(code);
        commonResult.setMsg(msg);
        return commonResult;
    }

    public static CommonResult ofSuccess(Object data) {
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccess(true);
        commonResult.setData(data);
        return commonResult;
    }

    public static CommonResult ofFail(String code, String msg) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(code);
        commonResult.setMsg(msg);
        return commonResult;
    }

    public CommonResult ofFail(String code, String msg, Object data) {
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccess(false);
        commonResult.setCode(code);
        commonResult.setMsg(msg);
        commonResult.setData(data);
        return commonResult;
    }

    public static CommonResult ofFail(CommonResultEnum commonErrorEnum) {
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccess(false);
        commonResult.setCode(commonErrorEnum.getCode());
        commonResult.setMsg(commonErrorEnum.getMessage());
        return commonResult;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
