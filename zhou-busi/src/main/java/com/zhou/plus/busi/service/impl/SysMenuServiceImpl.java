package com.zhou.plus.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.plus.busi.entity.SysMenu;
import com.zhou.plus.busi.mapper.SysMenuMapper;
import com.zhou.plus.busi.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-12
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    /**
     * 获取角色授权的菜单列表
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<SysMenu> getRoleMenuList(String userId) {
        return this.baseMapper.getRoleMenuList(userId);
    }


    /**
     * 获取当前用户菜单的权限标识
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<String> getPermissionMenuList(String userId) {
        return baseMapper.getPermissionMenuList(userId);
    }
}
