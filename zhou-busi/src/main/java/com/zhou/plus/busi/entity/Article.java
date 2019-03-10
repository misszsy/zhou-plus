package com.zhou.plus.busi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zhou.plus.busi.common.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("article")
public class Article extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 栏目id
     */
    private String columnId;

    /**
     * 文章类型
     */
    private String typeId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章图片
     */
    private String picture;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 描述说明
     */
    private String description;

    /**
     * 文章来源
     */
    private String source;

    /**
     * 文章状态(0:未发布,1:已发布,2:已取消)
     */
    private String status;

    /**
     * 是否推荐(0:否,1:是)
     */
    private String recommend;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    /**
     * 发布人
     */
    private String publishBy;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishDate;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 删除标志
     */
    @TableLogic
    private String disabled;
}
