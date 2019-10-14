package com.baizhi.serviceimpl;

import com.baizhi.entity.Chapter;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.ChapterService;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterMapper chapterMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryAll(Integer page, int rows, String albumId, HttpSession session) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        Integer count = chapterMapper.countAll(albumId);
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        Integer start = (page - 1) * rows;
        List<Chapter> albums = chapterMapper.queryAll(start, rows, albumId);
        Map<String, Object> map = new HashMap<>();
        map.put("rows", albums);
        map.put("records", count);
        map.put("page", page);
        map.put("total", total);
        return map;
    }

    @Override
    public void insert(Chapter chapter) {
        chapterMapper.insert(chapter);
    }

    @Override
    public void updateURL(Chapter chapter) {
        chapterMapper.updateURL(chapter);
    }

    @Override
    public void update(Chapter chapter) {
        chapterMapper.update(chapter);
    }

    @Override
    public void del(String[] id) {
        chapterMapper.del(id);
    }
}
