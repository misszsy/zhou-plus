package com.zhou.plus.admin.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhou.plus.admin.common.entity.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-08
 */
@TableName("sys_user")
@ApiModel(value = "SysUser类",description = "系统用户类")
public class SysUser extends BaseModel<SysUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 登录名称
     */
    @ApiModelProperty(name = "username",value = "登录名称",dataType = "String")
    private String username;

    /**
     * 登录密码
     */
    @ApiModelProperty(name = "password",value = "登录密码",dataType = "String")
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty(name = "name",value = "真实姓名",dataType = "String")
    private String name;

    /**
     * 删除标志(0:否,1:是)
     */
    @TableLogic
    @ApiModelProperty(name = "disabled",value = " 删除标志(0:否,1:是)",dataType = "String")
    private String disabled;

    @TableField(exist = false)
    @ApiModelProperty(name = "roleList",value = "角色信息集合",dataType = "SysRole")
    private List<SysRole> roleList;

    public SysUser(){
        super();
    }

    public SysUser(String id) {
        setId(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "SysUser{" +
        "id=" + getId() +
        ", username=" + username +
        ", password=" + password +
        ", name=" + name +
        ", disabled=" + disabled +
        "}";
    }
}
