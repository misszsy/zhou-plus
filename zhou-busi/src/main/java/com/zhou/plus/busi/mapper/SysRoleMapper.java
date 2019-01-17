package com.zhou.plus.busi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.plus.busi.entity.SysRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-13
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id查询是否是超级管理员
     */
    String getAdmin(@Param("userId") String userId);
}
