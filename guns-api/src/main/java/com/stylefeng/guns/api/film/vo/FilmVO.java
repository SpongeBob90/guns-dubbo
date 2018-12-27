package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : wyw 2018-12-27 10:27
 **/
@Data
public class FilmVO implements Serializable {

    private int filmNum;

    private List<FilmInfo> filmInfo;

}
