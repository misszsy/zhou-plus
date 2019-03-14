package com.zhou.plus.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.plus.busi.entity.Article;
import com.zhou.plus.busi.service.ArticleService;
import com.zhou.plus.framework.config.Global;
import com.zhou.plus.framework.resp.R;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * 成功案例
     */
    @GetMapping(value = {"case"})
    public String index(Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum) {
        IPage<Article> page=articleService.page(new Page<>(pageNum,6),new QueryWrapper<Article>().lambda()
                                                                                        .eq(Article::getDisabled, Global.FALSE)
                                                                                        .eq(Article::getStatus,Global.TURE)
                                                                                        .eq(Article::getColumnId,"3")
                                                                                         .orderByDesc(Article::getPublishDate));
        model.addAttribute("page",page);
        return "case/index";
    }



    /**
     * 成功案例
     */
    @GetMapping(value = {"case/{pageNum}"})
    @ResponseBody
    public R index(@PathVariable Integer pageNum) {
        IPage<Article> page=articleService.page(new Page<>(pageNum,3),new QueryWrapper<Article>().lambda()
                                                                            .eq(Article::getDisabled, Global.FALSE)
                                                                            .eq(Article::getStatus,Global.TURE)
                                                                            .eq(Article::getColumnId,"3")
                                                                            .orderByDesc(Article::getPublishDate));

        StringBuilder builder=new StringBuilder();
        Map<String,Object> map=new HashMap<>();

        for (Article article:page.getRecords()) {
            builder.append("<li>");
                builder.append("<div class='box'>");
                    builder.append("<div class='imgDiv'>");
                            builder.append("<img src='"+article.getPicture()+"' alt=''>");
                    builder.append("</div>");
                    builder.append("<div class='hideBox'>");
                            builder.append("<a href='/case/"+article.getId()+".html'>");
                                builder.append("<div class='name1'>"+article.getTitle()+"</div>");
                                builder.append("<div class='name2'></div>");
                            builder.append("</a>");
                    builder.append("</div>");
                builder.append("</div>");
            builder.append("</li>");
        }
        map.put("html",builder.toString());
        map.put("hasMore", CollectionUtils.isNotEmpty(page.getRecords())?"true":"false");
        return R.ok(map);
    }
}
