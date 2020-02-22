package com.lpc.utils;

import com.lpc.constants.BaseResponseConstants;
import com.lpc.responseConfig.BaseResponseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Lin
 * @Date 2020/2/22
 *
 * 供controller类使用
 */
public class ControllerUtils extends BaseResponseService {
    /**
     * 设置统一的错误响应方法
     */
    public static String setError(HttpServletRequest request, String msg, String location){
        request.setAttribute("error",msg);
        return location;
    }
    /**
     * 判断map集合的code 进行页面跳转
     */
    public static String get(HttpServletRequest request, Map<String, Object> map, String location, String error){
        if (map.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_500)){
            return setError(request,(String) map.get(BaseResponseConstants.HTTP_RESP_CODE_MSG),error);
        }
        Map<String, Object> dataMap = (Map<String, Object>) map.get(BaseResponseConstants.HTTP_RESP_CODE_DATA);
        request.setAttribute("dataMap", dataMap);
        return location;
    }

    /**
     * 字符串处理 货币形式变为数字形式
     */
    public static String convertToNumber(String string){
        String[] str = string.split(",");
        StringBuilder sb = new StringBuilder();
        for (String s : str){
            sb.append(s);
        }
        return sb.toString();
    }
}
