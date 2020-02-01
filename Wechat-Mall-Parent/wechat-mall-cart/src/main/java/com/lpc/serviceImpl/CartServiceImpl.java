package com.lpc.serviceImpl;

import com.lpc.constants.DBTable;
import com.lpc.redisConfig.BaseRedisService;
import com.lpc.responseConfig.BaseResponseService;
import cart.entity.Cart;
import cart.service.api.CartService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lpc.constants.Constants;
import com.lpc.dao.CartDao;
import com.lpc.utils.DateUtils;
import commodity.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Lin
 * @Date 2020/1/5
 */
@RestController
@Slf4j
@RequestMapping("/cart")
public class CartServiceImpl extends BaseResponseService implements CartService {
    @Autowired
    private BaseRedisService baseRedisService;
    @Autowired
    private CartDao cartDao;

    @Override
    public Map<String, Object> selectCart(@RequestParam("userId") String userId) {
        //直接进数据库查 跨库级联查询
        List<Item> itemList = cartDao.selectCart(userId);
        Map<String, Object> map = new HashMap<>();
        for (Item i : itemList) {
            map.put(i.getId().toString(),i);    //{"商品id":{....},"商品id":{....}}
        }
        return setResultSuccessData(map);
    }

    public Map<String, Object> addCart(@RequestParam("userId") String userId,
                                       @RequestParam("itemId") String itemId) {
        /////判断加没加购物车.在redis中判断即可
        //先获取redis中该用户全部的商品
        Set value = baseRedisService.getSet(userId);
        //Set转String  String再转JSONArray  Set无法直接转JSONArray
        JSONArray jsonArray = this.getJSONArrayBySet(value); //[{"1":"1"},{"2":"2"},...]
        //定义一个下标。遍历jsonObject中key对应的值，不等于就+1.如果index==json数组的size，就说明redis里面没有该商品
        int index = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (!itemId.equals(jsonObject.getString(itemId))){
                index++;
            }
        }
        if (index == value.size()){
            //添加进数据库
            Cart cart = new Cart();
            cart.setItemId(new Long(itemId));
            cart.setUserId(new Long(userId));
            cart.setCreatedTime(DateUtils.getTimeStamp());
            cart.setUpdatedTime(DateUtils.getTimeStamp());
            cartDao.save(cart, DBTable.TABLE_CART);
            //添加进redis
            JSONObject root = new JSONObject();
            root.put(itemId, itemId);
            baseRedisService.setSet(userId, root.toJSONString(), Constants.USER_SHOP_TERMOFVALIDITY);
        }
        //加了就不加
        else {
            return setResultError("该商品已经加入到购物车!");
        }
        return setResultSuccess("加入购物车成功!");
    }

    public Map<String, Object> delCart(@RequestParam("userId") String userId,
                                       @RequestParam("itemId") String itemId) {
        Set value = baseRedisService.getSet(userId); //{"userId":[{"1":"1"},{"2":"2"},...]}
        JSONArray jsonArray = this.getJSONArrayBySet(value); //[{"1":"1"},{"2":"2"},...]
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i); //{"1":"1"}
            if (itemId.equals(jsonObject.getString(itemId))){   // "1"
                //清除该用户redis中的指定商品
                baseRedisService.delSet(userId,jsonArray.getJSONObject(i).toJSONString()); //{"userId":[{"2":"2"}]}
                //清除该用户数据库中的指定商品
                cartDao.delCart(userId,itemId);
                return setResultSuccess("删除商品成功!");
            }
        }
        return setResultError("该商品没有加入购物车!");
    }
//
//    @Override
//    public Map<String, Object> emptyCart(@RequestParam("userId") String userId) {
//        Set value = baseRedisService.getSet(userId); //{"userId":[{"1":"1"},{"2":"2"},...]}
//        JSONArray jsonArray = this.getJSONArrayBySet(value); //[{"1":"1"},{"2":"2"},...]
//        if (value.size() == 0) {
//            return setResultError("您的购物车已经是空的了!");
//        }
//        //循环遍历清除该用户redis中的商品
//        for (int i = 0; i < jsonArray.size(); i++){
//            baseRedisService.delSet(userId,jsonArray.getJSONObject(i).toJSONString());
//        }
//        //清空该用户数据库中的全部商品
//        cartDao.emptyCart(userId);
//        return setResultSuccess("清空购物车成功!");
//    }

    @Override
    public Integer selectUserCartCount(@RequestParam("id") Long id) {
        return cartDao.selectUserCartCount(id);
    }

    private JSONArray getJSONArrayBySet(Set value){
        return JSONArray.parseArray("" + value);
    }
}
