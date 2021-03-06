package com.stylefeng.guns.gateway.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.vo.UserInfoVO;
import com.stylefeng.guns.api.user.vo.UserVO;
import com.stylefeng.guns.api.user.UserServiceAPI;
import com.stylefeng.guns.gateway.common.CurrentUser;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : wyw 2018-12-26 14:43
 **/
@RestController
@RequestMapping("/user/")
public class UserController {

    @Reference(interfaceClass = UserServiceAPI.class)
    private UserServiceAPI userServiceAPI;

    @PostMapping("register")
    public ResponseVO register(UserVO userVO) {
        if ((userVO.getUsername() == null) || (userVO.getUsername().trim().length() == 0)) {
            return ResponseVO.serviceFail("用户名不能为空");
        }
        if ((userVO.getPassword() == null) || (userVO.getPassword().trim().length() == 0)) {
            return ResponseVO.serviceFail("密码不能为空");
        }
        boolean isSuccess = userServiceAPI.register(userVO);
        if (isSuccess) {
            return ResponseVO.success("注册成功");
        } else {
            return ResponseVO.serviceFail("注册失败");
        }
    }

    @PostMapping("check")
    public ResponseVO check(String username) {
        if ((username != null) && (username.trim().length() > 0)) {
            boolean notExists = userServiceAPI.checkUsername(username);
            if (notExists) {
                return ResponseVO.success("用户名不存在");
            } else {
                return ResponseVO.serviceFail("用户名已存在");
            }
        } else {
            return ResponseVO.serviceFail("用户名不能为空");
        }
    }

    /**
     * 应用：
     *     1、前端存储JWT【7天】：JWT的刷新
     *     2、服务器端会存储活动用户信息【30分钟】
     *     3、JWT里的userId为key，查找活跃用户
     * 退出：
     *     1、前端删除掉JWT
     *     2、后端服务器删除活跃用户缓存
     * 现状：
     *     1、前端删除掉JWT
     */
    @GetMapping("logout")
    public ResponseVO logout() {
        return ResponseVO.success("用户退出成功");
    }

    @GetMapping("getUserInfo")
    public ResponseVO getUserInfo() {
        // 获取当前登陆用户
        String userId = CurrentUser.getUserId();
        if ((userId != null) && (userId.trim().length() > 0)) {
            // 将用户ID传入后端进行查询
            int uuid = Integer.parseInt(userId);
            UserInfoVO userInfo = userServiceAPI.getUserInfo(uuid);
            if (userInfo != null) {
                return ResponseVO.success(userInfo);
            } else {
                return ResponseVO.appFail("用户信息查询失败");
            }
        } else {
            return ResponseVO.serviceFail("用户未登陆");
        }
    }

    @PostMapping("updateUserInfo")
    public ResponseVO updateUserInfo(UserInfoVO userInfoVO) {
        // 获取当前登陆用户
        String userId = CurrentUser.getUserId();
        if ((userId != null) && (userId.trim().length() > 0)) {
            // 将用户ID传入后端进行查询
            int uuid = Integer.parseInt(userId);
            // 判断当前登陆人员的ID与修改的结果ID是否一致
            if (uuid != userInfoVO.getUuid()) {
                return ResponseVO.serviceFail("请修改您个人的信息");
            }
            UserInfoVO userInfo = userServiceAPI.updateUserInfo(userInfoVO);
            if (userInfo != null) {
                return ResponseVO.success(userInfo);
            } else {
                return ResponseVO.appFail("用户信息修改失败");
            }
        } else {
            return ResponseVO.serviceFail("用户未登陆");
        }
    }
}
