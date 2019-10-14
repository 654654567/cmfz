package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("admin")
    public Map<String, String> login(String username, String password, HttpServletRequest request, String enCode) {
        Integer num = (Integer) request.getSession().getAttribute("num");
        String code = num.toString();
        Map<String, String> map = new HashMap<>();
        Admin admin = adminService.login(username);
        if (admin == null) {
            String context = "账号不存在";
            map.put("context", context);
        } else if (!password.equals(admin.getPassword())) {
            String context = "密码错误";
            map.put("context", context);
        } else if (!code.equals(enCode)) {
            String context = "验证码不正确";
            map.put("context", context);
        } else {
            request.getSession().setAttribute("admin", admin);
            String context = "ok";
            map.put("context", context);
        }
        return map;
    }

}
