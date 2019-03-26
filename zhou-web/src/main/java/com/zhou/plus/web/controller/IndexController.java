package com.zhou.plus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhou.plus.busi.entity.Article;
import com.zhou.plus.busi.service.ArticleService;
import com.zhou.plus.framework.config.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 首页Controller
 * @author zhoushengyuan
 * @version 2019-01-27
 */
@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    /**
     *  首页
     */
    @GetMapping(value = {"/","index"})
    public String index(Model model) {
        Map<String,List<Article>> indexMap=articleService.getArticleIndexMap();
        model.addAttribute("indexMap", indexMap);
        model.addAttribute("article",articleService.getOne(new QueryWrapper<Article>().lambda()
                                                                .eq(Article::getDisabled, Global.FALSE)
                                                                .eq(Article::getRecommend,Global.TURE)
                                                                .eq(Article::getStatus,Global.TURE)
                                                                .orderByDesc(Article::getPublishDate).last("limit 1")));
        return "index";
    }
}
