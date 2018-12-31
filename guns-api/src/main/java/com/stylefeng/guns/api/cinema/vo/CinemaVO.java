package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wyw
 * @date 2018\12\31 0031 19:15
 */
@Data
public class CinemaVO implements Serializable {

    private String uuid;

    private String cinemaName;

    private String address;

    private String minimumPrice;

}
