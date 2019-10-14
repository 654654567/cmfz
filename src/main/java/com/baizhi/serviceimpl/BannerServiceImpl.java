package com.baizhi.serviceimpl;

import com.baizhi.entity.Banner;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerMapper bannerMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> queryAll(Integer start, Integer row) {
        Integer page = (start - 1) * row;
        return bannerMapper.queryAll(page, row);
    }

    @Override
    public void update(Banner banner) {
        bannerMapper.update(banner);
    }

    @Override
    public void delete(String[] ids) {
        bannerMapper.delete(ids);
    }

    @Override
    public void insert(Banner banner) {
        bannerMapper.insert(banner);
    }

    @Override
    public void updateURL(Banner banner) {
        bannerMapper.updateURL(banner);
    }

    @Override
    public Integer count() {
        return bannerMapper.count();
    }
}
