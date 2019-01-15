package com.zhou.plus.admin.modules.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhou.plus.admin.common.entity.BaseModel;
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
 * @since 2018-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
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
}
