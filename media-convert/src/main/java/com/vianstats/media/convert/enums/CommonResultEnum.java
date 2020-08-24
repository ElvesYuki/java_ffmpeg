package com.vianstats.media.convert.enums;

/**
 * 返回预定义消息和状态码
 */
public enum CommonResultEnum {
    SUCCESS("200", "success"),
    FAIL("500", "fail"),
    BUSINESS_ERROR("501", "业务类型错误");

    /**
     * 状态码
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    private CommonResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
