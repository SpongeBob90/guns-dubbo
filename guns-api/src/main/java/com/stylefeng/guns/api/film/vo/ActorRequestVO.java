package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : wyw 2018-12-29 09:13
 **/
@Data
public class ActorRequestVO implements Serializable {

    private ActorVO director;

    private List<ActorVO> actors;

}
