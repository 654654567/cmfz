package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public interface ChapterService {
    Map<String, Object> queryAll(Integer start, int rows, String albumId, HttpSession session) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException;

    void insert(Chapter chapter);

    void updateURL(Chapter chapter);

    void update(Chapter chapter);

    void del(String[] id);

}
