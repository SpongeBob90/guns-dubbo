package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wyw 2018-12-27 16:23
 **/
@Data
public class SourceVO implements Serializable {

    private String sourceId;

    private String sourceName;

    private boolean isActive;

}
