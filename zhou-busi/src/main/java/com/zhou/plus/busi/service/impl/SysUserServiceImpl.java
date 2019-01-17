package com.zhou.plus.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.plus.busi.common.utils.UserUtils;
import com.zhou.plus.busi.entity.SysRole;
import com.zhou.plus.busi.entity.SysUser;
import com.zhou.plus.busi.entity.SysUserRole;
import com.zhou.plus.busi.mapper.SysUserMapper;
import com.zhou.plus.busi.service.SysUserRoleService;
import com.zhou.plus.busi.service.SysUserService;
import com.zhou.plus.framework.exception.BusinessException;
import com.zhou.plus.framework.utils.CryptoUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 获取用户的角色信息
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<SysRole> getRoleByUserId(String id) {
        return this.baseMapper.getRoleByUserId(id);
    }


    /**
     * 新增用户
     * @param sysUser
     */
    @Transactional
    @Override
    public boolean save(SysUser sysUser){
        //密码加密String plaintextPassword = sysUser.getPassword();
        String encrypted =  CryptoUtils.encryptPassword(sysUser.getPassword());
        sysUser.setPassword(encrypted);
        super.save(sysUser);

        List<SysUserRole>  userRoleList=formmatSysUserRoleList(sysUser.getRoleList(),sysUser.getId());

        if(CollectionUtils.isNotEmpty(userRoleList)){
            sysUserRoleService.saveBatch(userRoleList);
        }else{
            throw new BusinessException(sysUser.getUsername()+"未设置角色信息");
        }
        return true;
    }

    /**
     * 更新用户信息
     * @param sysUser
     */
    @Transactional
    @Override
    public boolean updateById(SysUser sysUser){
        super.updateById(sysUser);

        List<SysUserRole>  userRoleList=formmatSysUserRoleList(sysUser.getRoleList(),sysUser.getId());

        if(CollectionUtils.isNotEmpty(userRoleList)){
            //删除该用户已存在的用户角色关系
            sysUserRoleService.remove(new QueryWrapper<SysUserRole>()
                              .lambda()
                              .eq(SysUserRole::getUserId,sysUser.getId()));

            sysUserRoleService.saveBatch(userRoleList);
        }else{
            throw new BusinessException(sysUser.getUsername()+"未设置角色信息");
        }
        UserUtils.clearCache(sysUser);
        return true;
    }


     private List<SysUserRole> formmatSysUserRoleList(List<SysRole> roleList, String userId) {
        List<SysUserRole> userRoleList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(roleList)) {
            for (SysRole role : roleList) {
                //用户角色关系
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(role.getId());
                sysUserRole.setUserId(userId);
                userRoleList.add(sysUserRole);
            }
        }
        return userRoleList;
    }
}
