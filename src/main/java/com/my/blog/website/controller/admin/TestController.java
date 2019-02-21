package com.my.blog.website.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping("/test")
    public String tester() {
        return "tester";
    }

    @RequestMapping("/helloAjax")
    public String ajaxTester() {
        return "helloAjax";
    }

}
