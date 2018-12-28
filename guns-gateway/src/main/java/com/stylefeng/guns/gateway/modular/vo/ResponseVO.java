package com.stylefeng.guns.gateway.modular.vo;

import lombok.Data;

/**
 * @author : wyw 2018-12-25 10:32
 **/
@Data
public class ResponseVO<T> {
    // 返回状态【0-成功，1-业务失败，999-系统异常】
    private int status;
    // 返回信息
    private String msg;
    // 返回数据
    private T data;

    private ResponseVO(){}

    public static<T> ResponseVO success(T t){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(t);
        return responseVO;
    }

    public static ResponseVO success(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);
        return responseVO;
    }

    public static<T> ResponseVO serviceFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);
        return responseVO;
    }

    public static<T> ResponseVO appFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);
        return responseVO;
    }

}
