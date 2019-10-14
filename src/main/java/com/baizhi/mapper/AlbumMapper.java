package com.baizhi.mapper;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper {
    List<Album> queryAll(@Param("start") Integer start, @Param("rows") Integer rows);

    Integer count();

    void insert(Album album);

    void updateURL(Album album);

    void update(Album album);

    void del(String[] id);

    void updateCount(Album album);
}
