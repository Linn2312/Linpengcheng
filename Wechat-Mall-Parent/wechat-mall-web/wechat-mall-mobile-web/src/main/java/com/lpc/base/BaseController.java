package com.lpc.base;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lin
 * @Date 2020/2/21
 */
public class BaseController{
    /**
     * 设置统一的错误响应方法
     */
    protected String setError(HttpServletRequest request, String msg, String location){
        request.setAttribute("error",msg);
        return location;
    }
}

