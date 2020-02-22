package com.lpc.controller;

import com.alibaba.fastjson.JSON;
import com.lpc.base.BaseController;
import com.lpc.constants.Constants;
import com.lpc.feign.CartFeign;
import com.lpc.feign.ItemFeign;
import com.lpc.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import member.entity.mb_user;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private ItemFeign itemFeign;

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
     * 加入购物车 需判断商品库存
     * @return
     */
    @GetMapping("/addCart")
    @ResponseBody
    public String addCart(HttpServletRequest request,
                                       @RequestParam("itemId") String itemId,
                                       @RequestParam("callback") String callback) {
        Map<String,Object> map;
        String token = CookieUtil.getUid(request, Constants.USER_TOKEN);
        if (StringUtils.isEmpty(token)) {
            map = setResultError("您没有登录,请先登录后,才可以加入到购物车.");
            return callback + "("+ JSON.toJSONString(map)+")";
        }
        Integer num = itemFeign.selectNum(itemId);
        if (num==0){
            map = setResultError("商品库存为0，请等候卖家进货");
            return callback + "("+ JSON.toJSONString(map)+")";
        }
        mb_user mb_user = super.getUserInfo(token);
        map = cartFeign.addCart(mb_user.getId().toString(), itemId);
        log.info(callback);
        return callback + "("+ JSON.toJSONString(map)+")";
    }

    /**
     * 删除指定商品
     */
    @RequestMapping("/delCart")
    public String delCart(@RequestParam("itemId") String itemId, HttpServletRequest request){
        //根据token换取userId
        String token = CookieUtil.getUid(request, Constants.USER_TOKEN);
        mb_user mb_user = super.getUserInfo(token);
        cartFeign.delCart(String.valueOf(mb_user.getId()),itemId);
        return "redirect:selectCart";
    }
}
