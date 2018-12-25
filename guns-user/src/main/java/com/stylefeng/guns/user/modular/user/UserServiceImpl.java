package com.stylefeng.guns.user.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.module.UserInfoModule;
import com.stylefeng.guns.api.module.UserModule;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.user.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.user.common.persistence.model.MoocUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wyw
 * @date 2018\12\20 0020 22:18
 */
@Component
@Service(interfaceClass = UserAPI.class)
public class UserServiceImpl implements UserAPI {

    @Autowired
    private MoocUserTMapper moocUserTMapper;

    @Override
    public boolean register(UserModule userModule) {
        // 将注册信息实体转换为数据实体
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userModule.getUsername());
        // 数据加密【MD5混淆加密( + 盐值)】
        String md5Password = MD5Util.encrypt(userModule.getPassword());
        moocUserT.setUserPwd(md5Password);
        moocUserT.setEmail(userModule.getEmail());
        moocUserT.setAddress(userModule.getAddress());
        moocUserT.setUserPhone(userModule.getPhone());
        Integer insert = moocUserTMapper.insert(moocUserT);
        if (insert > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int login(String userName, String password) {
        // 根据登陆账号获取数据库信息
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userName);
        // 根据用户名查询用户实体
        MoocUserT result = moocUserTMapper.selectOne(moocUserT);
        // 校验登陆密码是否匹配
        if ((result != null) && (result.getUuid() > 0)) {
            String md5Password = MD5Util.encrypt(password);
            if (result.getUserPwd().equals(md5Password)) {
                return result.getUuid();
            }
        }
        return 0;
    }

    @Override
    public boolean checkUsername(String username) {
        // 查询是否存在与用户名匹配的用户
        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", username);
        Integer result = moocUserTMapper.selectCount(entityWrapper);
        if ((result != null) && (result > 0)) {
            return false;
        } else {
            return true;
        }
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
