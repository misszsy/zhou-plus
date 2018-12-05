package com.zhou.plus.admin.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhou.plus.admin.common.entity.BaseModel;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-03
 */
@TableName("sys_log")
public class SysLog extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 操作用户
     */
    private String userId;

    /**
     * 日志类型(0:登录登出,1:操作日志,2:异常日志)
     */
    private String type;

    /**
     * 操作名称
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 设备名称
     */
    private String equipMent;

    /**
     * 操作系统
     */
    private String operationSystem;

    /**
     * 浏览器名
     */
    private String browser;

    /**
     * 响应时间
     */
    private Long respTime;

    /**
     * 操作时间
     */
    private LocalDateTime createDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
    public String getEquipMent() {
        return equipMent;
    }

    public void setEquipMent(String equipMent) {
        this.equipMent = equipMent;
    }
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Long getRespTime() {
        return respTime;
    }

    public void setRespTime(Long respTime) {
        this.respTime = respTime;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    @Override
    public String toString() {
        return "SysLog{" +
        "userId=" + userId +
        ", type=" + type +
        ", operation=" + operation +
        ", method=" + method +
        ", requestUrl=" + requestUrl +
        ", requestIp=" + requestIp +
        ", equipMent=" + equipMent +
        ", operationSystem=" + operationSystem +
        ", browser=" + browser +
        ", respDate=" + respTime +
        ", createDate=" + createDate +
        "}";
    }
}
