package com.zhou.plus.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.plus.busi.entity.Article;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-24
 */
public interface ArticleService extends IService<Article> {

    Map<String, List<Article>> getArticleIndexMap();
}
