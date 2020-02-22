package com.lpc.base;

import com.lpc.constants.BaseResponseConstants;
import com.lpc.responseConfig.BaseResponseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
     * 判断map集合的code 进行页面跳转
     */
    protected String get(HttpServletRequest request,Map<String, Object> map,String location,String error){
        if (map.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_500)){
            return setError(request,(String) map.get(BaseResponseConstants.HTTP_RESP_CODE_MSG),error);
        }
        Map<String, Object> dataMap = (Map<String, Object>) map.get(BaseResponseConstants.HTTP_RESP_CODE_DATA);
        request.setAttribute("dataMap", dataMap);
        return location;
    }

}
