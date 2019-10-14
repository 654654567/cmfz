package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;

public interface BannerService {
    List<Banner> queryAll(Integer start, Integer row);

    void update(Banner banner);

    void delete(String[] ids);

    void insert(Banner banner);

    void updateURL(Banner banner);

    Integer count();
}
