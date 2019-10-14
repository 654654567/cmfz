package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    private String id;
    private String title;
    private Double score;
    private String author;
    private String broadcast;
    private Integer chapter_count;
    private String context;
    private String state;
    private Date create_time;
    private String url;
}
