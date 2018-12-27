package com.stylefeng.guns.api.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : wyw 2018-12-24 11:17
 **/
@Data
public class UserVO implements Serializable {

    private String username;

    private String password;

    private String email;

    private String phone;

    private String address;

}
