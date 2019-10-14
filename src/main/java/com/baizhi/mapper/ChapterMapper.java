package com.baizhi.mapper;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterMapper {
    List<Chapter> queryAll(@Param("start") Integer start, @Param("rows") int rows, @Param("albumId") String albumId);

    Integer count();

    Integer countAll(String albumId);

    void insert(Chapter chapter);

    void updateURL(Chapter chapter);

    void update(Chapter chapter);

    void del(String[] id);

}
