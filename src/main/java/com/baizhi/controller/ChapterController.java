package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;
    @Autowired
    AlbumService albumService;
    @Autowired
    ChapterMapper chapterMapper;

    @RequestMapping("queryAll")
    public Map<String, Object> queryAll(Integer page, Integer rows, String albumId, HttpSession session) throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException {
        return chapterService.queryAll(page, rows, albumId, session);

    }

    @RequestMapping("edit")
    public String edit(Chapter chapter, String oper, String[] id, String albumId) {
        Album album = new Album();
        album.setId(albumId);
        Integer count = chapterMapper.countAll(albumId);
        System.out.println(count);
        if (oper.equals("add")) {
            album.setChapter_count(count + 1);
            System.out.println(album);
            albumService.updateCount(album);
            String s = UUID.randomUUID().toString();
            chapter.setId(s);
            chapter.setCreate_time(new Date());
            chapter.setAlbum_id(albumId);
            chapterService.insert(chapter);
            return s;
        } else if (oper.equals("del")) {
            int length = id.length;
            album.setChapter_count(count - length);
            albumService.updateCount(album);
            chapterService.del(id);
        } else {
            chapterService.update(chapter);
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile mp3_name, String chapterId, HttpSession session) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {

        //album.setSize(bg+"MB");
        String realPath = session.getServletContext().getRealPath("/mp3");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String originalFilename = mp3_name.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            mp3_name.transferTo(new File(realPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(newFileName);
        String realPath1 = session.getServletContext().getRealPath("/mp3/" + newFileName);
        System.out.println(realPath1);
        File file1 = new File(realPath1);
        long length = file1.length();
        //获取音频时长 单位是秒      AudioFile类需要引入额外依赖 jaudiotagger
        AudioFile read = AudioFileIO.read(file1);
        AudioHeader header = read.getAudioHeader();
        int trackLength = header.getTrackLength();
        //获取分钟数
        Integer m = trackLength / 60;
        //获取秒秒数
        Integer s = trackLength % 60;
        //album.setTime(m+"分"+s+"秒");
        double size = (double) length;
        //获取文件大小 单位是MB
        double ll = size / 1024 / 1024;
        //取double小数点后两位  四舍五入
        BigDecimal bg = new BigDecimal(ll).setScale(2, RoundingMode.UP);
        Chapter chapter = new Chapter();
        chapter.setSize(bg + "MB");
        chapter.setMp3_name(newFileName);
        chapter.setTime(m + "分" + s + "秒");
        chapter.setId(chapterId);
        chapterService.updateURL(chapter);
    }


    @RequestMapping("download")
    public void donwoad(String audioName, HttpSession session, HttpServletResponse response) throws IOException {
        System.out.println(audioName);
        String realPath = session.getServletContext().getRealPath("/mp3");
        File file = new File(realPath, audioName);
        //通过下划线分割，取第二个元素
        String s = audioName.split("_")[1];
        //编码格式转换为utf-8
        String encode = URLEncoder.encode(s, "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + encode);
        FileUtils.copyFile(file, response.getOutputStream());

    }
}
