package com.baizhi.mapper;

import com.baizhi.entity.User;

import java.util.List;

public interface UserMapper {
    List<User> queryAll();

    List<User> queryDays();
}
