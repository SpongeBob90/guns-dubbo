package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wyw
 * @date 2018\12\31 0031 19:24
 */
@Data
public class FilmInfoVO implements Serializable {

    private String filmId;

    private String filmName;

    private String filmLength;

    private String filmType;

    private String filmCats;

    private String actors;

    private String imgAddress;

    private List<FilmFieldVO> filmFields;

}
