package com.zhou.plus.busi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.plus.busi.entity.Column;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-24
 */
public interface ColumnMapper extends BaseMapper<Column> {

    List<Map<String,Object>> selectColumnMaps();
}
