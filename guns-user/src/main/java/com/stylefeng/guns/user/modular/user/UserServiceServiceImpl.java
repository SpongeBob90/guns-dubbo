package com.stylefeng.guns.user.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.user.vo.UserInfoVO;
import com.stylefeng.guns.api.user.vo.UserVO;
import com.stylefeng.guns.api.user.UserServiceAPI;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.user.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.user.common.persistence.model.MoocUserT;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author wyw
 * @date 2018\12\20 0020 22:18
 */
@Component
@Service(interfaceClass = UserServiceAPI.class, loadbalance = "roundrobin")
public class UserServiceServiceImpl implements UserServiceAPI {

    @Resource
    private MoocUserTMapper moocUserTMapper;

    @Override
    public boolean register(UserVO userVO) {
        // 将注册信息实体转换为数据实体
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userVO.getUsername());
        // 数据加密【MD5混淆加密( + 盐值)】
        String md5Password = MD5Util.encrypt(userVO.getPassword());
        moocUserT.setUserPwd(md5Password);
        moocUserT.setEmail(userVO.getEmail());
        moocUserT.setAddress(userVO.getAddress());
        moocUserT.setUserPhone(userVO.getPhone());
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
    public UserInfoVO getUserInfo(int uuid) {
        // 根据主键查询用户信息[MoocUserT]
        MoocUserT moocUserT = moocUserTMapper.selectById(uuid);
        // 将MoocUserT转换为UserInfoModel
        UserInfoVO userInfoVO = do2UserInfo(moocUserT);
        // 返回UserInfoModel
        return userInfoVO;
    }

    @Override
    public UserInfoVO updateUserInfo(UserInfoVO userInfoVO) {
        // 将传入的数据转换为MoocUserT
        MoocUserT moocUserT = do2MoocUserT(userInfoVO);
        // 将数据存入数据库
        Integer result = moocUserTMapper.updateById(moocUserT);
        if (result > 0) {
            // 按照Id将用户信息查出来
            UserInfoVO userInfo = getUserInfo(moocUserT.getUuid());
            // 返回给前端
            return userInfo;
        } else {
            return userInfoVO;
        }
    }

    private UserInfoVO do2UserInfo(MoocUserT moocUserT) {
        UserInfoVO userInfoVO = new UserInfoVO();

        userInfoVO.setUuid(moocUserT.getUuid());
        userInfoVO.setUsername(moocUserT.getAddress());
        userInfoVO.setUpdateTime(moocUserT.getUpdateTime().getTime());
        userInfoVO.setSex(moocUserT.getUserSex());
        userInfoVO.setPhone(moocUserT.getUserPhone());
        userInfoVO.setNickname(moocUserT.getNickName());
        userInfoVO.setLifeState("" + moocUserT.getLifeState());
        userInfoVO.setHeadAddress(moocUserT.getHeadUrl());
        userInfoVO.setEmail(moocUserT.getEmail());
        userInfoVO.setCreateTime(moocUserT.getBeginTime().getTime());
        userInfoVO.setBirthday(moocUserT.getBirthday());
        userInfoVO.setBiography(moocUserT.getBiography());
        userInfoVO.setAddress(moocUserT.getAddress());

        return userInfoVO;
    }

    private MoocUserT do2MoocUserT(UserInfoVO userInfoVO) {
        MoocUserT moocUserT = new MoocUserT();

        moocUserT.setUuid(userInfoVO.getUuid());
        moocUserT.setUserSex(userInfoVO.getSex());
        moocUserT.setUpdateTime(time2Date(System.currentTimeMillis()));
        moocUserT.setNickName(userInfoVO.getNickname());
        moocUserT.setLifeState(Integer.parseInt(userInfoVO.getLifeState()));
        moocUserT.setHeadUrl(userInfoVO.getHeadAddress());
        moocUserT.setBirthday(userInfoVO.getBirthday());
        moocUserT.setBiography(userInfoVO.getBiography());
        moocUserT.setBeginTime(time2Date(userInfoVO.getCreateTime()));
        moocUserT.setEmail(userInfoVO.getEmail());
        moocUserT.setAddress(userInfoVO.getAddress());
        moocUserT.setUserPhone(userInfoVO.getPhone());
        moocUserT.setUserName(userInfoVO.getUsername());

        return moocUserT;
    }

    private Date time2Date(long time) {
        Date date = new Date(time);
        return date;
    }

}
