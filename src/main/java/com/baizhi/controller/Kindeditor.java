package com.baizhi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/kindeditor")
@RestController
public class Kindeditor {
    @RequestMapping("/upload")
    public Map<String, Object> upload(MultipartFile img, HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String realPath = request.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String newFileName = new Date().getTime() + "_" + img.getOriginalFilename();
        img.transferTo(new File(realPath, newFileName));
        //获取当前网站的协议名
        String scheme = request.getScheme();
        //获取本机ip
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        //获取端口号
        int port = request.getServerPort();
        //获取项目名
        String path = request.getServletPath();
        System.out.println(path);
        String url = scheme + "://" + hostAddress + ":" + port + "/cmfz" + "/img/" + newFileName;
        map.put("error", 0);
        map.put("url", url);
        return map;
    }

    @RequestMapping("allImages")
    public Map<String, Object> all(HttpSession session, HttpServletRequest request) throws UnknownHostException {
        Map<String, Object> map1 = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        String realPath = session.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        String[] allImg = file.list();
        for (String s : allImg) {
            Map<String, Object> map = new HashMap<>();
            map.put("is_dir", false);
            map.put("has_file", false);
            //获取文件大小
            File file1 = new File(realPath, s);
            long length = file1.length();
            map.put("filesize", length);
            map.put("dir_path", "");
            map.put("is_photo", true);
            //获取文件后缀名
            String s1 = s.substring(s.lastIndexOf(".") + 1);
            map.put("filetype", s1);
            map.put("filename", s);
            if (s.contains("_")) {
                String s2 = s.split("_")[0];
                Long aLong = Long.valueOf(s2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String format = simpleDateFormat.format(aLong);
                map.put("datetime", format);
            } else {
                map.put("datetime", new Date());
            }
            list.add(map);
        }
        map1.put("moveup_dir_path", "");
        map1.put("current_dir_path", "");
        //获取当前网站的协议名  http
        String scheme = request.getScheme();
        //获取当前本机ip地址
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        //获取当前端口号
        int port = request.getServerPort();
        //获取项目名
        String path = request.getContextPath();
        String url = scheme + "://" + hostAddress + ":" + port + path + "/img/";
        map1.put("current_url", url);
        int size = list.size();
        map1.put("total_count", size);
        map1.put("file_list", list);
        return map1;
    }
}
