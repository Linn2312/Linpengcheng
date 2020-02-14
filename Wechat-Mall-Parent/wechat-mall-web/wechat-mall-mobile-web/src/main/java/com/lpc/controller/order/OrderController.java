package com.lpc.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.lpc.constants.BaseResponseConstants;
import com.lpc.constants.Constants;
import com.lpc.controller.base.BaseController;
import com.lpc.feign.item.ItemFeign;
import com.lpc.feign.order.OrderFeign;
import com.lpc.utils.CookieUtil;
import com.lpc.utils.DateUtils;
import commodity.entity.Item;
import member.entity.mb_user;
import order.entity.OrderInfo;
import order.entity.OrderVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Lin
 * @Date 2020/1/17
 */
@Controller
public class OrderController extends BaseController {
    private static final String ERROR = "error";
    private static final String ORDERDETAIL = "orderDetail";
    private static final String PAY = "pay";
    private static final String MYORDER = "myOrder";
    @Autowired
    private OrderFeign orderFeign;
    @Autowired
    private ItemFeign itemFeign;

    @PostMapping("/generateOrder")
    public String generateOrder(HttpServletRequest request){
        ////去结算。订单信息、订单详情 两张表的信息在这一个方法体中一起生成    数据前台页面都传过来

        //获取前台传来的参数(json字符串)
        String J_itemIds = request.getParameter("J_itemIds");
        String J_totalPrice = request.getParameter("J_totalPrice");
        String J_shoppingnums = request.getParameter("J_shoppingnums");
        //解析json字符串
        OrderVo orderVo = this.parseJson(J_itemIds, J_totalPrice, J_shoppingnums);
        //解析完成 赋值
        List<Long> itemIds = orderVo.getItemIds();
        List<Long> shoppingnums = orderVo.getShoppingnums();
        Double amount = orderVo.getAmount();

        //获取用户id
        String token = CookieUtil.getUid(request, Constants.USER_TOKEN);
        mb_user mb_user = super.getUserInfo(token);
        if (mb_user==null){
            return super.setError(request, "系统正忙，请稍后再试",ERROR);
        }
        Long userId = mb_user.getId();
        //生成订单信息
        Map<String, Object> map1 = orderFeign.addOrderInfo(userId, amount);
        if (map1.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_500)){
            return super.setError(request,(String) map1.get(BaseResponseConstants.HTTP_RESP_CODE_MSG),ERROR);
        }
        //立马获取刚才生成的订单id
        Long orderId = Long.parseLong(String.valueOf(map1.get(BaseResponseConstants.HTTP_RESP_CODE_DATA)));
        if (map1.get(BaseResponseConstants.HTTP_RESP_CODE_NAME) == BaseResponseConstants.HTTP_RESP_CODE_200){
            return super.setError(request,"订单创建失败，请稍后重试",ERROR);
        }

        //获取购物车选择的商品信息 用于显示到订单详情页面
        List<Item> list = itemFeign.selectItemsByIds(itemIds);
        if (list==null){
            return super.setError(request,"系统正忙，请稍后再试",ERROR);
        }
        Byte orderStatus = 0;
        for (int i = 0; i < list.size(); i++) {
            //往list里面依次添加shoppingnum
            list.get(i).setShoppingnum(shoppingnums.get(i));
            list.get(i).setOrderStatus(orderStatus);
            //依次生成订单详情
            Map<String, Object> map = orderFeign.addOrderDetail(orderId, itemIds.get(i), shoppingnums.get(i));
            if (map.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_500)){
                return super.setError(request,(String) map.get(BaseResponseConstants.HTTP_RESP_CODE_MSG),ERROR);
            }
        }
        request.setAttribute("amount",amount);
        request.setAttribute("orderId",orderId);
        request.setAttribute("createdTime", DateUtils.getTimeStamp());
        request.setAttribute("listOrder",list);
        return ORDERDETAIL;
    }

    @RequestMapping("/cancelOrder")
    public String cancelOrder(HttpServletRequest request,@RequestParam("orderId")String id){
        String orderId = super.convertToNumber(id);
        Long Oid = Long.parseLong(orderId);
        Map<String, Object> map = orderFeign.cancelOrder(Oid);
        if (map.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_500)){
            return super.setError(request,(String) map.get(BaseResponseConstants.HTTP_RESP_CODE_MSG),ERROR);
        }
        return "redirect:selectCart";
    }

    @RequestMapping("/toOrderDetail")
    public String toOrderDetail(HttpServletRequest request,@RequestParam("orderId") String id,
                                @RequestParam("amount")String amount,@RequestParam("createdTime")String createdTime){
        String orderId = super.convertToNumber(id);
        List<Item> list = this.getItemsIdByOrderId(Long.parseLong(orderId));
        if (list==null){
            return super.setError(request,"系统正忙，请稍后再试",ERROR);
        }
        request.setAttribute("amount",super.convertToNumber(amount));
        request.setAttribute("listOrder",list);
        request.setAttribute("createdTime",createdTime);
        request.setAttribute("orderId",orderId);
        return ORDERDETAIL;
    }

    @RequestMapping("/orderToPay")
    public String orderToPay(HttpServletRequest request,
                             @RequestParam("orderId") String id,
                             @RequestParam("amount") String amount){
        //总价钱 订单id 货币形式转为数字形式
        Long totalPrice = Long.parseLong(super.convertToNumber(amount));
        String orderId = super.convertToNumber(id);
        //商品的信息、购买数量   先查订单中商品的id，再根据商品id查商品信息
        List<Item> itemList = this.getItemsIdByOrderId(Long.parseLong(orderId));
        if (itemList==null){
            return super.setError(request,"系统正忙，请稍后再试",ERROR);
        }
        //查询买家的信息 姓名手机号地址
        String token = CookieUtil.getUid(request, Constants.USER_TOKEN);
        mb_user mb_user = super.getUserInfo(token);
        request.setAttribute("address",mb_user.getAddress());
        request.setAttribute("phone",mb_user.getPhone());
        request.setAttribute("username",mb_user.getUsername());
        request.setAttribute("totalPrice",totalPrice);
        request.setAttribute("itemList",itemList);
        request.setAttribute("orderId",orderId);
        return PAY;
    }

    @RequestMapping("/selectUserAllOrder")
    public String selectUserAllOrder(HttpServletRequest request){
        String token = CookieUtil.getUid(request,Constants.USER_TOKEN);
        mb_user mb_user = super.getUserInfo(token);
        if (mb_user==null){
            return super.setError(request, "系统正忙，请稍后再试",ERROR);
        }
        List<OrderInfo> listAll = orderFeign.selectUserAllOrder(mb_user.getId());
        if (listAll==null){
            return super.setError(request,"系统正忙，请稍后再试",ERROR);
        }
        List<OrderInfo> listPay = new ArrayList<>();
        List<OrderInfo> listNoPay = new ArrayList<>();
        List<OrderInfo> listOverTimePay = new ArrayList<>();
        for (OrderInfo orderInfo : listAll) {
            if (orderInfo.getStatus() == 0) {
                listNoPay.add(orderInfo);
            }
            if (orderInfo.getStatus() == 1){
                listPay.add(orderInfo);
            }
            if (orderInfo.getStatus() == 2){
                listOverTimePay.add(orderInfo);
            }
        }
        //循环遍历orderId，进行List<Item>拼接
        List<Item> itemNoPay = this.getItemNoOrPay(listNoPay);
        List<Item> itemPay = this.getItemNoOrPay(listPay);
        List<Item> itemOverTimePay = this.getItemNoOrPay(listOverTimePay);

        request.setAttribute("itemPay",itemPay);
        request.setAttribute("itemNoPay",itemNoPay);
        request.setAttribute("itemOverTimePay",itemOverTimePay);
        return MYORDER;
    }

    /**
     * 解析前台传来的json字符串
     */
    private OrderVo parseJson(String J_itemIds, String J_totalPrice, String J_shoppingnums){
        //解析json字符串
        JSONArray jsonArray_0 = JSONArray.parseArray(J_itemIds);
        JSONArray jsonArray_1 = JSONArray.parseArray(J_shoppingnums);
        List<Long> itemIds = new ArrayList<>();
        List<Long> shoppingnums = new ArrayList<>();
        for (int i = 0; i < jsonArray_0.size(); i++) {
            Long id = Long.parseLong((String) jsonArray_0.get(i));
            itemIds.add(id);
            Long shoppingnum = Long.parseLong((String) jsonArray_1.get(i));
            shoppingnums.add(shoppingnum);
        }
        Double amount = Double.parseDouble(J_totalPrice);
        //解析完成
        OrderVo orderVo = new OrderVo();
        orderVo.setAmount(amount);
        orderVo.setItemIds(itemIds);
        orderVo.setShoppingnums(shoppingnums);
        return orderVo;
    }

    /**
     * 根据订单id查询商品的id，再根据商品id查商品信息  将商品的购买数量和订单的支付状态和订单id一同封装起来
     */
    private List<Item> getItemsIdByOrderId(Long orderId){
        List<Long> itemsIdList = orderFeign.getItemsIdByOrderId(orderId);
        if (itemsIdList==null){
            return null;
        }
        List<Long> shoppingnumList = orderFeign.getShoppingnum(orderId);
        Byte orderStatus = orderFeign.getOrderStatus(orderId);
        List<Item> list = itemFeign.selectItemsByIds(itemsIdList);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setShoppingnum(shoppingnumList.get(i));
            list.get(i).setOrderStatus(orderStatus);
            list.get(i).setOrderId(orderId);
        }
        return list;
    }

    /**
     * 循环遍历orderId，进行List<Item>拼接
     */
    private List<Item> getItemNoOrPay(List<OrderInfo> list){
        List<Item> item = new ArrayList<>();
        for (OrderInfo orderInfo : list) {
            item.addAll(this.getItemsIdByOrderId(orderInfo.getId()));
        }
        return item;
    }
}
