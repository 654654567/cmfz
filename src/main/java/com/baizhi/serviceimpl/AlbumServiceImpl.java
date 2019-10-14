package com.baizhi.serviceimpl;

import com.baizhi.entity.Album;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumMapper albumMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Integer count = albumMapper.count();
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        Integer start = (page - 1) * rows;
        List<Album> albums = albumMapper.queryAll(start, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("rows", albums);
        map.put("records", count);
        map.put("page", page);
        map.put("total", total);
        return map;
    }

    @Override
    public void insert(Album album) {
        albumMapper.insert(album);
    }

    @Override
    public void updateURL(Album album) {
        albumMapper.updateURL(album);
    }

    @Override
    public void update(Album album) {
        albumMapper.update(album);
    }

    @Override
    public void del(String[] id) {
        albumMapper.del(id);
    }

    @Override
    public void updateCount(Album album) {
        albumMapper.updateCount(album);
    }
}
