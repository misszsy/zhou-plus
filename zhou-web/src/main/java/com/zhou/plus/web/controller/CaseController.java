package com.zhou.plus.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CaseController {

    /**
     * 前后端分离，跳转到前端的主页
     */
    @GetMapping(value = {"case"})
    public String index() {
        return "case/index";
    }
}
