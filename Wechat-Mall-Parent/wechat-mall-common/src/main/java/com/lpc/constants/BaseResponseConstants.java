package com.lpc.constants;

/**
 * @author Lin
 * @Date 2019/12/5
 *
 * 存放响应状态的常量信息 包括相应状态码及响应的相关信息等等
 * 接口中常量的默认修饰符是 public static final
 */
public interface BaseResponseConstants {
    //响应编码
    String HTTP_RESP_CODE_NAME = "code";
    //响应msg
    String HTTP_RESP_CODE_MSG = "msg";
    //响应data
    String HTTP_RESP_CODE_DATA = "data";
    //响应请求成功
    String HTTP_RESP_CODE_200_VALUE = "success";
    //系统错误
    String HTTP_RESP_CODE_500_VALUE = "error";
    //响应请求成功的编码
    Integer HTTP_RESP_CODE_200 = 200;
    //响应请求失败的错误编码
    Integer HTTP_RESP_CODE_500 = 500;
    //请求参数错误编码
    Integer HTTP_REQ_CODE_400 = 400;

}
