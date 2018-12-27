package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wyw 2018-12-27 16:24
 **/
@Data
public class YearVO implements Serializable {

    private String yearId;

    private String yearName;

    private boolean isActive;

}
