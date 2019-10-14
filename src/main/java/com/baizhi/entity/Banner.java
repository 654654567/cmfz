package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    @Excel(name = "编号")
    private String id;
    @Excel(name = "名字")
    private String name;
    @Excel(name = "标题")
    private String context;
    @Excel(name = "创建时间", databaseFormat = "yyyy-MM-dd", format = "yyyy-MM-dd")
    private Date create_date;
    @Excel(name = "状态")
    private String state;
    @Excel(name = "图片", type = 2)
    private String url;
}
