package com.lpc.serviceImpl;

import com.lpc.constants.DBTable;
import com.lpc.dao.OrderDao;
import com.lpc.responseConfig.BaseResponseService;
import com.lpc.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import order.entity.OrderDetail;
import order.entity.OrderInfo;
import order.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author Lin
 * @Date 2020/1/15
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderServiceImpl extends BaseResponseService implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public Map<String, Object> addOrderInfo(@RequestParam("userId")Long userId,
                                            @RequestParam("amount")Double amount) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAmount(amount);
        Byte status = 0;    //订单刚创建 状态是未付款 0
        orderInfo.setStatus(status);
        orderInfo.setUserId(userId);
        orderInfo.setCreatedTime(DateUtils.getTimeStamp());
        orderInfo.setUpdatedTime(DateUtils.getTimeStamp());
        orderDao.saveOrderInfo(orderInfo);
        Long orderInfoId = orderInfo.getId();
        if (orderInfoId == null) {
            return setResultError("系统错误,未获取到订单id");
        }
        return setResultSuccess("创建订单成功",orderInfoId);
    }

    @Override
    public Map<String, Object> addOrderDetail(@RequestParam("orderId")Long orderId,
                                              @RequestParam("itemId")Long itemId,
                                              @RequestParam("shoppingnum")Long shoppingnum) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setShoppingnum(shoppingnum);
        orderDetail.setItemId(itemId);
        orderDetail.setOrderId(orderId);
        orderDetail.setCreatedTime(DateUtils.getTimeStamp());
        orderDetail.setUpdatedTime(DateUtils.getTimeStamp());
        orderDao.save(orderDetail, DBTable.ORDERDETAIL);
        return setResultSuccess("订单详情添加成功");
    }

    @Override
    public Map<String, Object> updateOrderStatus(@RequestParam("orderInfoId")Long orderInfoId,
                                                 @RequestParam("status")Byte status) {
        Timestamp updatedTime = DateUtils.getTimeStamp();
        orderDao.updateOrderStatus(orderInfoId,status,updatedTime);
        return setResultSuccess("订单状态修改成功");
    }

    @Override
    public List<OrderInfo> selectAllOrders() {
        return orderDao.selectAllOrders();
    }

    @Override
    public List<OrderInfo> selectUserAllOrder(@RequestParam("userId")Long userId) {
        return orderDao.selectUserAllOrder(userId);
    }

    @Override
    public Map<String, Object> cancelOrder(@RequestParam("orderId") Long orderId) {
        orderDao.delOrder(orderId);
        orderDao.delOrderDetail(orderId);
        return setResultSuccess("取消订单成功");
    }

    @Override
    public List<Long> getItemsIdByOrderId(@RequestParam("orderId") Long orderId) {
        return orderDao.getItemsIdByOrderId(orderId);
    }

    @Override
    public List<Long> getShoppingnum(@RequestParam("orderId") Long orderId) {
        return orderDao.getShoppingnum(orderId);
    }

    @Override
    public Integer selectUserOrderCount(@RequestParam("id") Long id) {
        return orderDao.selectUserOrderCount(id);
    }

    @Override
    public Byte getOrderStatus(@RequestParam("orderId")Long orderId) {
        return orderDao.getOrderStatus(orderId);
    }

    @Override
    public Map<String, Object> updateOrderInfo(@RequestBody OrderInfo orderInfo) {
        orderInfo.setUpdatedTime(DateUtils.getTimeStamp());
        orderDao.updateOrderInfo(orderInfo);
        return setResultSuccess();
    }
}
