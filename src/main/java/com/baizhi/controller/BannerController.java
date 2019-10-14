package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @RequestMapping("findAll")
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Integer count = bannerService.count();
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        List<Banner> list = bannerService.queryAll(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("records", count);
        map.put("page", page);
        map.put("total", total);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Banner banner, String oper, String[] id) {
        if (oper.equals("add")) {
            String s = UUID.randomUUID().toString();
            banner.setId(s);
            banner.setCreate_date(new Date());
            bannerService.insert(banner);
            return s;
        } else if (oper.equals("del")) {
            bannerService.delete(id);
            return null;
        } else {
            bannerService.update(banner);
            return null;
        }
    }

    @RequestMapping("upload")
    public void upload(MultipartFile url, String bannerId, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String originalFilename = url.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            url.transferTo(new File(realPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setUrl(newFileName);
        bannerService.updateURL(banner);
    }

    @RequestMapping("download")
    public void download() throws IOException {
        List<Banner> banners = bannerService.queryAll(1, 1000000000);
        for (Banner banner : banners) {
            String url = banner.getUrl();
            banner.setUrl("E:\\后期项目\\cmfz\\src\\main\\webapp\\img/" + url);
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图信息表", "表1"),
                Banner.class, banners);
        workbook.write(new FileOutputStream(new File("D:/easy.xls")));
    }
}
