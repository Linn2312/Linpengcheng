package com.lpc.utils;


import java.util.UUID;

/**
 * @author Lin
 * @Date 2019/12/11
 */
public class TokenUtils {

    /**
     * 生成一个登录成功的token
     */
    public static String getToken(){
        return UUID.randomUUID().toString();
    }
    /**
     * 生成一个支付的token
     */
    public static String getPayToken() {
        return "pay-" + UUID.randomUUID().toString();
    }
}
