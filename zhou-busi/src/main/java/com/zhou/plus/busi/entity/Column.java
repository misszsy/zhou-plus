package com.zhou.plus.busi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhou.plus.busi.common.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

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
@TableName("sys_column")
public class Column extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 栏目名称
     */
    private String name;

    /**
     * 英文名称
     */
    private String enName;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 英文名称
     */
    @TableField(exist = false)
    private String parentName;

    @TableField(exist = false)
    private List<Column> children;
}
