package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wyw
 * @date 2018\12\31 0031 19:17
 */
@Data
public class BrandVO implements Serializable {

    private String brandId;

    private String brandName;

    private String isActive;

}
