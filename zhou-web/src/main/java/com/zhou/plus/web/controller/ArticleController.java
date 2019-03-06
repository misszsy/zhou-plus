package com.zhou.plus.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {

    /**
     * 前后端分离，跳转到前端的主页
     */
    @GetMapping(value = {"article"})
    public String index() {
        return "article/index";
    }
}
