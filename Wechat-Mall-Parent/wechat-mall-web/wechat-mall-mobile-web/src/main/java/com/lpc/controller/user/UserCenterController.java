package com.lpc.controller.user;

import com.lpc.constants.Constants;
import com.lpc.controller.base.BaseController;
import com.lpc.feign.cart.CartFeign;
import com.lpc.feign.order.OrderFeign;
import com.lpc.utils.CookieUtil;
import member.entity.mb_user;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lin
 * @Date 2020/1/13
 */
@Controller
public class UserCenterController extends BaseController {
    private static final String USER_CENTER = "usercenter";
    private static final String LOGIN = "login";
    private static final String ERROR = "error";
    @Autowired
    private OrderFeign orderFeign;
    @Autowired
    private CartFeign cartFeign;

    @RequestMapping("/toUserCenter")
    public String toUserCenter(HttpServletRequest request){
        String token = CookieUtil.getUid(request, Constants.USER_TOKEN);
        if (StringUtils.isNotBlank(token)){
            mb_user mb_user = super.getUserInfo(token);
            if (mb_user==null){
                return super.setError(request, "系统正忙，请稍后再试",ERROR);
            }
            request.setAttribute("username",mb_user.getUsername());

            Integer cartSize = cartFeign.selectUserCartCount(mb_user.getId());
            if (cartSize==null){
                return super.setError(request,"系统正忙，请稍后再试",ERROR);
            }
            request.setAttribute("cartSize",cartSize);

            Integer orderSize = orderFeign.selectUserOrderCount(mb_user.getId());
            if (orderSize==null){
                return super.setError(request,"系统正忙，请稍后再试",ERROR);
            }
            request.setAttribute("orderSize",orderSize);
        }
        return USER_CENTER;
    }

    @RequestMapping("/exit")
    public String exit(HttpServletResponse response){
        CookieUtil.removeCookie(response,Constants.USER_TOKEN);
        return LOGIN;
    }
}
