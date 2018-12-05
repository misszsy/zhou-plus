package com.zhou.plus.admin.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.plus.admin.modules.entity.SysMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-12
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> getRoleMenuList(String userId);

    List<String> getPermissionMenuList(String userId);
}
