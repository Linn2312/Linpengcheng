package com.lpc.controller.cart;

import com.lpc.constants.Constants;
import com.lpc.controller.base.BaseController;
import com.lpc.utils.CookieUtil;
import com.lpc.feign.cart.CartFeign;
import lombok.extern.slf4j.Slf4j;
import member.entity.mb_user;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Lin
 * @Date 2020/1/5
 */
@Controller
@Slf4j
public class CartController extends BaseController {
    private static final String CART = "cart";
    private static final String ERROR = "error";
    @Autowired
    private CartFeign cartFeign;

    /**
     * 查询该用户的购物车
     */
    @RequestMapping("/selectCart")
    public String selectCart(HttpServletRequest request){
        String token = CookieUtil.getUid(request, Constants.USER_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return super.setError(request,"您没有登录,请先登录后,才可以查询购物车.",ERROR);
        }
        mb_user mb_user = super.getUserInfo(token);
        if (mb_user==null){
            return super.setError(request, "系统正忙，请稍后再试",ERROR);
        }
        //查询购物车中的商品信息
        Map<String, Object> map = cartFeign.selectCart(mb_user.getId().toString());
        return super.get(request,map,CART,ERROR);
    }

    /**
     * 加入购物车
     * @return
     */
    @PostMapping("/addCart")
    @ResponseBody
    public Map<String, Object> addCart(HttpServletRequest request, String itemId) {
        String token = CookieUtil.getUid(request, Constants.USER_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return setResultError("您没有登录,请先登录后,才可以加入到购物车.");
        }
        mb_user mb_user = super.getUserInfo(token);
        if (mb_user == null) {
            return setResultError("系统正忙，请稍后再试");
        }
        return cartFeign.addCart(mb_user.getId().toString(), itemId);
    }

    /**
     * 删除指定商品
     */
    @RequestMapping("/delCart")
    public String delCart(@RequestParam("itemId") String itemId, HttpServletRequest request){
        //根据token换取userId
        String token = CookieUtil.getUid(request, Constants.USER_TOKEN);
        mb_user mb_user = super.getUserInfo(token);
        if (mb_user==null){
            return super.setError(request, "系统正忙，请稍后再试",ERROR);
        }
        cartFeign.delCart(String.valueOf(mb_user.getId()),itemId);
        return "redirect:selectCart";
    }
}
