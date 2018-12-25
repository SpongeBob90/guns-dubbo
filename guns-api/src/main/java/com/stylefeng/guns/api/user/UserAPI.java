package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.module.UserInfoModule;
import com.stylefeng.guns.api.module.UserModule;

/**
 * @author wyw
 * @date 2018\12\20 0020 22:16
 */
public interface UserAPI {

    int login(String userName, String password);

    boolean register(UserModule userModule);

    boolean checkUsername(String username);

    UserInfoModule getUserInfo(int uuid);

    UserInfoModule updateUserInfo(UserInfoModule userInfoModule);

}
