package com.zhou.plus.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页Controller
 * @author zhoushengyuan
 * @version 2019-01-27
 */
@Controller
public class IndexController {


    /**
     * 前后端分离，跳转到前端的主页
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
