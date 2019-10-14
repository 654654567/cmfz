package com.baizhi;

import com.baizhi.entity.*;
import com.baizhi.mapper.*;
import com.baizhi.serviceimpl.AdminServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {

    @Autowired
    AdminServiceImpl adminServiceImpl;

    @Test
    public void contextLoads() {
        Admin login = adminServiceImpl.login("admin");
        System.out.println(login);
    }

    @Autowired
    BannerMapper bannerMapper;

    @Test
    public void contextLoads1() {
        List<Banner> banners = bannerMapper.queryAll(0, 1);
        Integer count = bannerMapper.count();
        System.out.println(count);
        for (Banner banner : banners) {
            System.out.println(banner);
        }
    }

    @Test
    public void contextLoads2() {
        Banner banner = new Banner();
        String s = UUID.randomUUID().toString();
        banner.setId("2");
        banner.setName("小明");
        banner.setContext("这也是一张图片");
        banner.setCreate_date(new Date());
        banner.setState("否");
        banner.setUrl(UUID.randomUUID().toString());
        bannerMapper.insert(banner);
    }

    @Test
    public void contextLoads3() {
        Banner banner = new Banner();
        banner.setId("1");
        banner.setUrl(UUID.randomUUID().toString());
        bannerMapper.updateURL(banner);
    }

    @Test
    public void contextLoads4() {
        Banner banner = new Banner();
        String s = UUID.randomUUID().toString();
        banner.setId("1");
        banner.setName("王五");
        banner.setContext("111111");
        banner.setState("否");
        banner.setUrl("123");
        System.out.println(banner);
        bannerMapper.update(banner);
    }

    @Autowired
    AlbumMapper albumMapper;

    @Test
    public void test1() {
        Integer count = albumMapper.count();
        System.out.println(count);
    }

    @Autowired
    ChapterMapper chapterMapper;

    @Test
    public void test2() {
        List<Chapter> chapters = chapterMapper.queryAll(0, 5, "1");
        for (Chapter chapter : chapters) {
            System.out.println(chapter);
        }
    }

    @Autowired
    ArticleMapper articleMapper;

    @Test
    public void test3() {
        Integer count = articleMapper.count();
        List<Article> albums = articleMapper.queryAll(0, 10);
        System.out.println(count);
        for (Article album : albums) {
            System.out.println(album);
        }
    }

    @Autowired
    UserMapper userMapper;

    @Test
    public void test4() {
        List<User> users = userMapper.queryDays();
        for (User user : users) {
            System.out.println(user.getCreate_date());
            System.out.println(user.getValue());

        }
    }

}
