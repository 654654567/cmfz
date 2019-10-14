package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    Map<String, Object> queryAll(Integer page, Integer rows);

    void insert(Album album);

    void updateURL(Album album);

    void update(Album album);

    void del(String[] id);

    void updateCount(Album album);
}
