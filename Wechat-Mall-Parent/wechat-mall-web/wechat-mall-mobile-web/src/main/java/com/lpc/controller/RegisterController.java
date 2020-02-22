package com.lpc.controller;

import com.lpc.constants.BaseResponseConstants;
import com.lpc.constants.Constants;
import com.lpc.feign.UserFeign;
import com.lpc.utils.ControllerUtils;
import member.entity.mb_user;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Lin
 * @Date 2019/12/12
 */
@Controller
public class RegisterController {
    private static final String LOGIN = "login";
    private static final String REGISTER = "register";
    @Autowired
    private UserFeign userFeign;

    /**
     * 跳转到注册页面
     */
    @RequestMapping("/toRegister")
    public String toRegister(){
        return REGISTER;
    }

    /**
     * 实现注册功能
     */
    @RequestMapping("/register")
    public String register(HttpServletRequest request, mb_user mb_user){
        //如果是qq登录后关联新账户 需要封装openID
        String openID = (String) request.getSession().getAttribute(Constants.USER_OPENID);
        if (!StringUtils.isEmpty(openID)){
            mb_user.setOpenID(openID);
        }
        //调用feign客户端实现注册功能   返回json信息(code、msg)
        Map<String, Object> map = userFeign.register(mb_user);
        //如果注册没成功
        if (!map.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_200)) {
            String msg = (String) map.get(BaseResponseConstants.HTTP_RESP_CODE_MSG);
            return ControllerUtils.setError(request,msg,REGISTER);
        }
        //注册成功 清除session中的openID 防止重复使用
        if (!StringUtils.isEmpty(openID)){
            request.getSession().removeAttribute(Constants.USER_OPENID);
        }
        return LOGIN;
    }
}
