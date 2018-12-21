package com.stylefeng.guns.user.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
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
    public boolean login(String userName, String password) {
        System.out.println("This is user service!" + userName + "，" + password);
        return false;
    }

}
