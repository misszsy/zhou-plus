package com.zhou.plus.admin.modules.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhou.plus.admin.common.entity.BaseModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-17
 */
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "SysDict{" +
        "type=" + type +
        ", label=" + label +
        ", value=" + value +
        ", sort=" + sort +
        ", disabled=" + disabled +
        "}";
    }
}
