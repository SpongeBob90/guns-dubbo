package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wyw 2018-12-27 10:09
 **/
@Data
public class Banner implements Serializable {

    private String bannerId;

    private String bannerAddress;

    private String bannerUrl;

}
