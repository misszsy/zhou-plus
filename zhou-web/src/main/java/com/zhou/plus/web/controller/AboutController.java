package com.zhou.plus.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {


    /**
     * 关于我们
     */
    @GetMapping(value = {"about"})
    public String index() {
        return "about/index";
    }
}
