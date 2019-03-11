package com.zhou.plus.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {


    /**
     * 联系我们
     */
    @GetMapping(value = {"contact"})
    public String index() {
        return "contact/index";
    }
}
