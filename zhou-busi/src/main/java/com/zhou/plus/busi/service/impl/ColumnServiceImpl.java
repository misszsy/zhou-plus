package com.zhou.plus.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.plus.busi.entity.Column;
import com.zhou.plus.busi.mapper.ColumnMapper;
import com.zhou.plus.busi.service.ColumnService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  栏目服务实现类
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-24
 */
@Service
public class ColumnServiceImpl extends ServiceImpl<ColumnMapper, Column> implements ColumnService {



    @Override
    public List<Column> list(Wrapper<Column> queryWrapper) {
        List<Column> columnList =super.list(queryWrapper);
        return tree(columnList);
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<Column> queryWrapper) {
        return baseMapper.selectColumnMaps();
    }


  /*  *//**
     * 栏目分组管理
     * @return
     *//*
    @Override
    public List<Column> groupingByList(String columnId) {
        Map<String, List<Column>> columnMap = (Map<String, List<Column>>) JedisUtils.getObject(GlobalConsts.CACHE_COLUMN_CHILDREN_MAP);
        if(MapUtils.isEmpty(columnMap)){
            List<Column> columnList= baseMapper.selectChildrenList();

            columnMap=columnList.stream().collect(Collectors.groupingBy(Column::getParentId));

            JedisUtils.setObject(GlobalConsts.CACHE_COLUMN_CHILDREN_MAP,columnMap,0);
        }
        List<Column> columnList = columnMap.get(columnId);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(columnList)){
            columnList =new ArrayList<>();
        }
        return columnList;
    }*/



    /**
     * 构建树
     */
    private static List<Column> tree(List<Column> list){
        Map<String, Column> map = new LinkedHashMap<>();
        for (Column column : list){
            map.put(column.getId(), column);
        }

        for (Column column : list){
            String parentId = column.getParentId();

            if(column.getParentId() == null || "0".equals(column.getParentId())){
                continue;
            }

            Column parent = map.get(parentId);
            List<Column> childrenList = parent.getChildren();

            if(CollectionUtils.isEmpty(childrenList)){
                childrenList = new ArrayList<>();
                parent.setChildren(childrenList);
            }

            childrenList.add(column);
        }

        List<Column> firstLevel = new ArrayList<>();

        for (Column column : list){
            if(column.getParentId() == null || "0".equals(column.getParentId())){
                firstLevel.add(column);
            }
        }

        return firstLevel;
    }
}
