package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wyw 2018-12-27 10:28
 **/
@Data
public class FilmInfo implements Serializable {

    private String filmId;

    private int filmType;

    private String imgAddress;

    private String filmName;

    private String filmScore;

    private int expectNum;

    private String showTime;

    private int boxNum;

}
