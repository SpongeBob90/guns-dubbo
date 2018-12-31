package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wyw
 * @date 2018\12\31 0031 19:20
 */
@Data
public class CinemaInfoVO implements Serializable {

    private String cinemaId;

    private String imgUrl;

    private String cinemaName;

    private String cinemaAddress;

    private String cinemaPhone;

}
