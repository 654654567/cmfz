package com.baizhi.controller;

import io.goeasy.GoEasy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("goeasy")
public class GoEasyController {
    @ResponseBody
    @RequestMapping("lts")
    public void lts(String context) {
        GoEasy goEasy = new GoEasy("https://rest-hangzhou.goeasy.io", "BC-ad807c30743c43c69b5f98598d90354c");
        goEasy.publish("lts", context);
    }
}
