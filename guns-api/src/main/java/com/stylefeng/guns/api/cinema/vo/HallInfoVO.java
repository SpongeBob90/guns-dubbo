package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wyw
 * @date 2018\12\31 0031 19:27
 */
@Data
public class HallInfoVO implements Serializable {

    private String hallFieldId;

    private String hallName;

    private String price;

    private String seatFile;

    private String soldSeats;

}
