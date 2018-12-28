package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : wyw 2018-12-27 10:27
 **/
@Data
public class FilmVO implements Serializable {
    // 影片数量
    private int filmNum;
    // 图片前缀
    private String imgPre;
    // 当前页
    private int nowPage;
    // 总页数
    private int totalPage;
    // 影片信息
    private List<FilmInfo> filmInfo;
}
