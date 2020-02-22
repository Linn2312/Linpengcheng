package com.lpc.base;

import com.alibaba.fastjson.JSONObject;
import com.lpc.constants.BaseResponseConstants;
import com.lpc.feign.UserFeign;
import com.lpc.responseConfig.BaseResponseService;
import member.entity.mb_user;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Lin
 * @Date 2019/12/11
 */
public class BaseController extends BaseResponseService {
    @Autowired
    private UserFeign userFeign;

    /**
     * 根据token 进入redis获取userId，再进入数据库获取用户信息
     */
    protected mb_user getUserInfo(String token){
        Map<String, Object> userInfo = userFeign.getUserInfo(token);    //最终返回的是map,存储着msg、code、data
        //先验证状态码是不是200
        Integer code = (Integer) userInfo.get(BaseResponseConstants.HTTP_RESP_CODE_NAME);
        if (!code.equals(BaseResponseConstants.HTTP_RESP_CODE_200)){
            return null;
        }
        //获取data参数  解析成json格式并返回
        LinkedHashMap linkedHashMap = (LinkedHashMap) userInfo.get(BaseResponseConstants.HTTP_RESP_CODE_DATA);
        String data = new JSONObject().toJSONString(linkedHashMap);
        mb_user mb_user = new JSONObject().parseObject(data, member.entity.mb_user.class);
        return mb_user;
    }

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
