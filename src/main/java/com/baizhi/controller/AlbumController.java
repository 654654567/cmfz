package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RequestMapping("album")
@RestController
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @RequestMapping("queryAll")
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> map = albumService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Album album, String oper, String[] id) {
        if (oper.equals("add")) {
            String s = UUID.randomUUID().toString();
            album.setId(s);
            album.setCreate_time(new Date());
            album.setChapter_count(0);
            albumService.insert(album);
            return s;
        } else if (oper.equals("del")) {
            albumService.del(id);
        } else {
            albumService.update(album);
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile url, String albumId, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String originalFilename = url.getOriginalFilename();
        String newFileName = new Date().getTime() + originalFilename;
        try {
            url.transferTo(new File(realPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Album album = new Album();
        album.setId(albumId);
        album.setUrl(newFileName);
        albumService.updateURL(album);

    }
}
