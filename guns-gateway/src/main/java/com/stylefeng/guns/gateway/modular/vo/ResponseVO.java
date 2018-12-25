package com.stylefeng.guns.gateway.modular.vo;

/**
 * @author : wyw 2018-12-25 10:32
 **/
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
