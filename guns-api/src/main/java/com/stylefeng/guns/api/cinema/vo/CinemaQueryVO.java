package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wyw
 * @date 2018\12\31 0031 18:51
 */
@Data
public class CinemaQueryVO implements Serializable {

    private Integer brandId = 99;

    private Integer districtId = 99;

    private Integer hallType = 99;

    private Integer areaId = 99;

    private Integer pageSize = 12;

    private Integer nowPage = 1;

}
