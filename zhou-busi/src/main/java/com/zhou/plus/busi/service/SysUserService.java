package com.zhou.plus.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.plus.busi.entity.SysRole;
import com.zhou.plus.busi.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-08
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户id获取其用户的角色列表
     * @param id
     * @return
     */
    List<SysRole> getRoleByUserId(String id);
}
