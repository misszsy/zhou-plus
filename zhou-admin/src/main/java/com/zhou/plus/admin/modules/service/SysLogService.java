package com.zhou.plus.admin.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.plus.admin.modules.entity.SysLog;

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
