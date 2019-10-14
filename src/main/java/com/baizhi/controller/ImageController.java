package com.baizhi.controller;

import com.baizhi.captcha.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.DeploymentException;
import java.io.IOException;

@Controller
@RequestMapping("code")
public class ImageController {
    @RequestMapping("code")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws IOException, DeploymentException {
        String random = CaptchaUtil.random(request);
        HttpSession session = request.getSession();
        session.setAttribute("code", random);
        CaptchaUtil.outputImage(random, response.getOutputStream());
    }
}
