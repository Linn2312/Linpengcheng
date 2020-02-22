package com.lpc.base;

import com.lpc.responseConfig.BaseResponseService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lin
 * @Date 2019/12/11
 */
public class BaseController extends BaseResponseService {

    /**
     * 设置统一的错误响应方法
     */
    protected String setError(HttpServletRequest request, String msg, String location){
        request.setAttribute("error",msg);
        return location;
    }

    /**
     * 字符串处理 货币形式变为数字形式
     */
    protected String convertToNumber(String string){
        String[] str = string.split(",");
        StringBuilder sb = new StringBuilder();
        for (String s : str){
            sb.append(s);
        }
        return sb.toString();
    }


}
