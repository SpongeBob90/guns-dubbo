package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wyw 2018-12-29 09:17
 **/
@Data
public class InfoRequstVO implements Serializable {

    private String biography;

    private ActorRequestVO actors;

    private ImgVO imgVO;

    private String filmId;

}
