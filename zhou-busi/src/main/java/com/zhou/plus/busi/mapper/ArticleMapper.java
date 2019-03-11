package com.zhou.plus.busi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhou.plus.busi.entity.Article;
import org.apache.ibatis.annotations.Param;

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
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<Map<String,Object>> selectPageMaps(IPage page, @Param("article") Article article);

    List<Article> getArticleIndexList();
}
