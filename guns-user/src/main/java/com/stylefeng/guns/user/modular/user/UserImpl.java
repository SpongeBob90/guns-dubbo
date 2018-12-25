package com.stylefeng.guns.user.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.module.UserInfoModule;
import com.stylefeng.guns.api.module.UserModule;
import com.stylefeng.guns.api.user.UserAPI;
import org.springframework.stereotype.Component;

/**
 * @author wyw
 * @date 2018\12\20 0020 22:18
 */
@Component
@Service(interfaceClass = UserAPI.class)
public class UserImpl implements UserAPI {

    @Override
    public int login(String userName, String password) {
        return -1;
    }

    @Override
    public boolean register(UserModule userModule) {
        return false;
    }

    @Override
    public boolean checkUsername(String username) {
        return false;
    }

    @Override
    public UserInfoModule getUserInfo(int uuid) {
        return null;
    }

    @Override
    public UserInfoModule updateUserInfo(UserInfoModule userInfoModule) {
        return null;
    }

}
