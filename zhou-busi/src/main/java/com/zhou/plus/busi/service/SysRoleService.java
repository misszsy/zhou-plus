package com.zhou.plus.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.plus.busi.entity.SysRole;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-13
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据用户id查询是否是超级管理员
     */
    String getAdmin(String userId);
}
