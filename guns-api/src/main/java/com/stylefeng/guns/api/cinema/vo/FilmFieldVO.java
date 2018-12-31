package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wyw
 * @date 2018\12\31 0031 19:22
 */
@Data
public class FilmFieldVO implements Serializable {

    private String fieldId;

    private String beginTime;

    private String endTime;

    private String language;

    private String hallName;

    private String price;

}
