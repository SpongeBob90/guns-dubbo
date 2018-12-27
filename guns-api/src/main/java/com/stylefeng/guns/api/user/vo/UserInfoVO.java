package com.stylefeng.guns.api.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wyw 2018-12-24 11:23
 **/
@Data
public class UserInfoVO implements Serializable {

    private Integer uuid;

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private int sex;

    private String birthday;

    private String lifeState;

    private String biography;

    private String address;

    private String headAddress;

    private long createTime;

    private long updateTime;

}
