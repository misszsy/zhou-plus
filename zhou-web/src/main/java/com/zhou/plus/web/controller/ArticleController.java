package com.zhou.plus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.plus.busi.entity.Article;
import com.zhou.plus.busi.service.ArticleService;
import com.zhou.plus.framework.config.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 新闻资讯
     */
    @GetMapping(value = {"article"})
    public String index(Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum) {
        IPage<Article> page=articleService.page(new Page<>(pageNum,6),new QueryWrapper<Article>().lambda()
                                                                                                          .eq(Article::getDisabled, Global.FALSE)
                                                                                                          .eq(Article::getStatus,Global.TURE)
                                                                                                          .eq(Article::getColumnId,"4")
                                                                                                          .orderByDesc(Article::getPublishDate));
        model.addAttribute("page",page);
        return "article/index";
    }


    /**
     * 文章详情
     */
    @GetMapping(value = {"article/detail/{id}"})
    public String detail(@PathVariable String id,Model model) {
        model.addAttribute("article",articleService.getById(id));
        return "article/detail";
    }
}
