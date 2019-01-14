package com.zhou.plus.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        String root="https://blog.csdn.net/qq_36373262/article/details/60870832," +
                "https://blog.csdn.net/zclcqlove/article/details/79962270";
        SpringApplication.run(AdminApplication.class, args);
    }
}