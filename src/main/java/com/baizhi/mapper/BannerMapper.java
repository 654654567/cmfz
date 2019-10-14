package com.baizhi.mapper;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {
    List<Banner> queryAll(@Param("start") Integer start, @Param("row") Integer row);

    void update(Banner banner);

    void delete(String[] ids);

    void insert(Banner banner);

    void updateURL(Banner banner);

    Integer count();
}
