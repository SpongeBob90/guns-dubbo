package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wyw 2018-12-27 16:19
 **/
@Data
public class CatVO implements Serializable {

    private String catId;

    private String catName;

    private boolean isActive;

}
