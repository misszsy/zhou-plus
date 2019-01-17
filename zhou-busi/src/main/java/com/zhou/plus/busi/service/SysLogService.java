package com.zhou.plus.busi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.plus.busi.entity.SysLog;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-03
 */
public interface SysLogService extends IService<SysLog> {

    IPage<Map<String, Object>> selectPageMaps(Page page, SysLog log);
}
