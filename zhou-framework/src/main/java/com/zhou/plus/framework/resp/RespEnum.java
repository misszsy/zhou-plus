package com.zhou.plus.framework.resp;

/**
 * @author bone
 * @version 2017-07-27
 */
public enum RespEnum {

    /**
     * 执行成功
     */
    OK(200),

    /**
     * 执行失败
     */
    FAIL(400),

    /**
     * Token失效 ，session过期
     */
    NOT_LOGIN(401),

    /**
     * 没有操作权限
     */
    NOT_AUTH(403),

    /**
     * 系统异常
     */
    ERROR(500);

    /**
     * 状态码
     */
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    RespEnum(int code) {
        this.code = code;
    }
}
