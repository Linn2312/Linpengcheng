package com.lpc.controller.user;

import com.lpc.constants.BaseResponseConstants;
import com.lpc.controller.base.BaseController;
import com.lpc.feign.user.UserFeign;
import member.entity.mb_user;
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
public class RegisterController extends BaseController {
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
        //调用feign客户端实现注册功能   返回json信息(code、msg)
        Map<String, Object> map = userFeign.register(mb_user);
        //如果注册没成功
        if (!map.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_200)) {
            String msg = (String) map.get(BaseResponseConstants.HTTP_RESP_CODE_MSG);
            return super.setError(request,msg,REGISTER);
        }
        //注册成功
        return LOGIN;
    }
}
