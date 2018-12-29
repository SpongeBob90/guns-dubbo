package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wyw 2018-12-28 16:34
 **/
@Data
public class FilmDetailVO implements Serializable {

    private String imgPre;

    private String filmId;

    private String filmName;

    private String filmEnName;

    private String imgAddress;

    private String score;

    private String scoreNum;

    private String totalBox;

    private String info01;

    private String info02;

    private String info03;

    private InfoRequstVO info04;

}
