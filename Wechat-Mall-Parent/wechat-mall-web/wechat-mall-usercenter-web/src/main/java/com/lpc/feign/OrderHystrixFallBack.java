package com.lpc.feign;

import com.lpc.responseConfig.BaseResponseService;
import order.entity.OrderInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Lin
 * @Date 2020/1/29
 */
@Component
public class OrderHystrixFallBack extends BaseResponseService implements OrderFeign{

    @Override
    public Map<String, Object> addOrderInfo(Long userId, Double amount) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> addOrderDetail(Long orderId, Long itemId, Long shoppingnum) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> updateOrderStatus(Long orderInfoId, Byte status) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public List<OrderInfo> selectUserAllOrder(Long userId) {
        return null;
    }

    @Override
    public Map<String, Object> cancelOrder(Long orderId) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public List<Long> getItemsIdByOrderId(Long orderId) {
        return null;
    }

    @Override
    public List<Long> getShoppingnum(Long orderId) {
        return null;
    }

    @Override
    public Integer selectUserOrderCount(Long id) {
        return null;
    }

    @Override
    public Byte getOrderStatus(Long orderId) {
        return null;
    }

    @Override
    public List<OrderInfo> selectAllOrders() {
        return null;
    }

    @Override
    public Map<String, Object> updateOrderInfo(OrderInfo orderInfo) {
        return setResultError("系统正忙，请稍后再试");
    }
}
