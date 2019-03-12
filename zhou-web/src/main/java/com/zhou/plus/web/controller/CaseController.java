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

@Controller
public class CaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * 成功案例
     */
    @GetMapping(value = {"case"})
    public String index(Model model) {
        IPage<Article> page=articleService.page(new Page<>(1,6),new QueryWrapper<Article>().lambda()
                                                                                        .eq(Article::getDisabled, Global.FALSE)
                                                                                        .eq(Article::getStatus,Global.TURE)
                                                                                        .eq(Article::getColumnId,"3")
                                                                                         .orderByDesc(Article::getPublishDate));
        model.addAttribute("page",page);
        return "case/index";
    }


    /**
     * 案例详情
     */
    @GetMapping(value = {"case/detail/{id}"})
    public String detail(@PathVariable String id, Model model) {
        model.addAttribute("article",articleService.getById(id));
        return "case/detail";
    }
}
