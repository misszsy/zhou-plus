package com.zhou.plus.admin.modules.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhou.plus.admin.common.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict")
public class SysDict extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 排序权重
     */
    private Integer sort;

    /**
     * 删除状态
     */
    @TableLogic
    private String disabled;
}
