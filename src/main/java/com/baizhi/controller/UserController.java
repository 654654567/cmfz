package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("queryAll")
    public List<User> queryAll() {
        List<User> users = userService.queryAll();
        return users;
    }

    @RequestMapping("queryDays")
    public Map<String, List> queryDays() {
        List<User> users = userService.queryDays();
        List<Date> dates = new ArrayList<>();
        List<Integer> strings = new ArrayList<>();
        for (User user : users) {
            dates.add(user.getCreate_date());
            strings.add(user.getValue());
        }
        HashMap<String, List> map = new HashMap<>();
        map.put("xxx", dates);
        for (Date d : dates) {
            System.out.println(d);
        }
        map.put("yyy", strings);
        return map;
    }
}
