package com.zhou.plus.admin.modules.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhou.plus.admin.common.entity.BaseModel;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-12
 */
@TableName("sys_menu")
public class SysMenu extends BaseModel<SysMenu> {

    private static final long serialVersionUID = 1L;

    private String parentId;

    private String type;

    private String name;

    private Integer sort;

    private String href;

    private String icon;

    private String permission;

    @TableLogic
    @JSONField(serialize=false)
    private String disabled;

    @TableField(exist = false)
    private List<SysMenu> children;

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                "parentId='" + parentId + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", href='" + href + '\'' +
                ", icon='" + icon + '\'' +
                ", permission='" + permission + '\'' +
                ", disabled='" + disabled + '\'' +
                ", children=" + children +
                ", id='" + id + '\'' +
                '}';
    }
}
