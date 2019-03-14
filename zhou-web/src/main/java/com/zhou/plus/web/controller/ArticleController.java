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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping(value = {"{routes}/detail/{id}"})
    public String detail(@PathVariable String routes,@PathVariable String id,Model model) {
        Map<String,Object> articleMap=articleService.getMap(new QueryWrapper<Article>().setEntity(new Article().setId(id)));
        if(MapUtils.getLong(articleMap,"previous")==null){
            articleMap.put("previous",0);
        }
        if(MapUtils.getLong(articleMap,"next")==null){
            articleMap.put("next",0);
        }
        model.addAttribute("article",articleMap);
        return routes+"/detail";
    }


    /**
     * 成功案例
     */
    @GetMapping(value = {"article/{pageNum}"})
    @ResponseBody
    public R index(@PathVariable Integer pageNum) {
        IPage<Article> page=articleService.page(new Page<>(pageNum,3),new QueryWrapper<Article>().lambda()
                                                                                        .eq(Article::getDisabled, Global.FALSE)
                                                                                        .eq(Article::getStatus,Global.TURE)
                                                                                        .eq(Article::getColumnId,"4")
                                                                                        .orderByDesc(Article::getPublishDate));

        StringBuilder builder=new StringBuilder();
        for (Article article:page.getRecords()) {
            builder.append("<li>");
                builder.append("<div class='box'>");
                    builder.append("<div class='imgDiv'>");
                        builder.append("<a href='javascript:void(0)'>");
                            builder.append("<img src='"+article.getPicture()+"' alt=''>");
                        builder.append("</a>");
                        builder.append("<div class='hideBox'>");
                             builder.append("<div class='more'>");
                                builder.append("<a href='/article/"+article.getId()+".html'>更多</a>");
                             builder.append("</div>");
                        builder.append("</div>");
                    builder.append("</div>");
                    builder.append("<div class='name'>");
                             builder.append("<a href='/article/"+article.getId()+".html'>"+article.getTitle()+"</a>");
                    builder.append("</div>");
                    builder.append("<div class='content'>"+article.getDescription()+"</div>");
                    builder.append("<div class='botDiv'>");
                        builder.append("<div class='time'>"+ DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(article.getPublishDate())+"</div>");
                        builder.append("<a href='/article/"+article.getId()+".html' class='add'><img th:src='/static/images/nimg11_1.png}'  alt=''></a>");
                    builder.append("</div>");
                builder.append("</div>");
            builder.append("</li>");
        }

        Map<String,Object> map=new HashMap<>();
        map.put("html",builder.toString());
        map.put("hasMore", CollectionUtils.isNotEmpty(page.getRecords())?"true":"false");
        return R.ok(map);
    }
}
