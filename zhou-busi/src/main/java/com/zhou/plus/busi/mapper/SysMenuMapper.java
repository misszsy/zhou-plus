package com.zhou.plus.busi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.plus.busi.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-12
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户id获取其对应的角色菜单列表
     * @param userId
     * @return
     */
    List<SysMenu> getRoleMenuList(@Param("userId") String userId);


    List<String> getPermissionMenuList(String userId);
}
