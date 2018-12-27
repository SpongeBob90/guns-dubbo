package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.vo.UserInfoVO;
import com.stylefeng.guns.api.user.vo.UserVO;

/**
 * @author wyw
 * @date 2018\12\20 0020 22:16
 */
public interface UserServiceAPI {

    int login(String userName, String password);

    boolean register(UserVO userVO);

    boolean checkUsername(String username);

    UserInfoVO getUserInfo(int uuid);

    UserInfoVO updateUserInfo(UserInfoVO userInfoVO);

}
