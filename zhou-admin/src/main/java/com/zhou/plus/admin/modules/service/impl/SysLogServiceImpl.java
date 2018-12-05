package com.zhou.plus.admin.modules.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.plus.admin.modules.entity.SysLog;
import com.zhou.plus.admin.modules.mapper.SysLogMapper;
import com.zhou.plus.admin.modules.service.SysLogService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-03
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public IPage<Map<String, Object>> selectPageMaps(Page page, SysLog sysLog) {
        return baseMapper.selectPageMaps(page,sysLog);
    }
}
