package com.vianstats.media.convert.advice;

/**
 * @author liangwj
 * @date 2020-06-23
 */
public class VianException extends RuntimeException {
    private static final long serialVersionUID = -1L;

    /**
     * 错误码
     */
    protected String errCode;

    /**
     * 错误消息
     */
    protected String errMsg;

    public VianException() {
        super();
    }

    public VianException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public VianException(String errMsg) {
        this.errMsg = errMsg;
    }


    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
