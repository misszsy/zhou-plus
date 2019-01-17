package com.zhou.plus.busi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.plus.busi.entity.SysRole;
import com.zhou.plus.busi.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-08
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户id获取其用户的角色列表
     * @param id
     * @return
     */
    List<SysRole> getRoleByUserId(@Param("id") String id);
}
