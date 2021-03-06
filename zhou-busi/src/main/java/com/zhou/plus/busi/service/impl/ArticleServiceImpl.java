package com.zhou.plus.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.plus.busi.entity.Article;
import com.zhou.plus.busi.mapper.ArticleMapper;
import com.zhou.plus.busi.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *  新闻服务实现类
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-24
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public IPage<Map<String, Object>> pageMaps(IPage<Article> page, Wrapper<Article> queryWrapper) {
        return baseMapper.selectPageMaps(page,queryWrapper.getEntity());
    }


    /**
     * 获取新闻资讯和成功案例
     * @return
     */
    @Override
    public Map<String, List<Article>> getArticleIndexMap() {
        Map<String,List<Article>> mapList=baseMapper.getArticleIndexList()
                .stream().collect(Collectors.groupingBy(Article::getTypeId));
        return Optional.ofNullable(mapList).orElseGet(HashMap::new);
    }


    /**
     * 根据id查询文章详情上下篇
     * @param queryWrapper
     * @return
     */
    @Override
    public Map<String, Object> getMap(Wrapper<Article> queryWrapper) {
        Map<String, Object> map=baseMapper.getArticleMap(queryWrapper.getEntity().getId());
        return Optional.ofNullable(map).orElseGet(HashMap::new);
    }
}
