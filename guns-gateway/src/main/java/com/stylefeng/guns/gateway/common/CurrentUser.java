package com.stylefeng.guns.gateway.common;

/**
 * @author : wyw 2018-12-25 11:02
 **/
public class CurrentUser {

    // 线程绑定的存储空间
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    // 将当前用户的userId存放到线程存储空间中
    public static void saveUserId(String userId){
        threadLocal.set(userId);
    }

    // 从线程存储空间中获取当前用户的userId
    public static String getUserId(){
        return threadLocal.get();
    }

}
