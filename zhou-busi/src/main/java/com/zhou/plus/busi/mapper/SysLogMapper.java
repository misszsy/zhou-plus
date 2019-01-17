package com.zhou.plus.busi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.plus.busi.entity.SysLog;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-03
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

    IPage<Map<String,Object>> selectPageMaps(Page page, SysLog log);
}
