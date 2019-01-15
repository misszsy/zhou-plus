package com.zhou.plus.admin.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhou.plus.admin.common.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
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
}
