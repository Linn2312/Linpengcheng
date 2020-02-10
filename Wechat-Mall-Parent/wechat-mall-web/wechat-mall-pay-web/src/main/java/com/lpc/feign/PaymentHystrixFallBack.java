package com.lpc.feign;

import com.lpc.responseConfig.BaseResponseService;
import order.entity.OrderInfo;
import org.springframework.stereotype.Component;
import pay.entity.PaymentInfo;

import java.util.Map;

/**
 * @author Lin
 * @Date 2020/1/29
 */
@Component
public class PaymentHystrixFallBack extends BaseResponseService implements PaymentInfoFeign,PaymentTypeFeign{

    @Override
    public Map<String, Object> addPaymentInfo(String orderId, String totalPrice) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> getPayInfoByToken(String token) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> getPayInfoByOrderId(String orderId) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> updatePayInfo(PaymentInfo paymentInfo) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> updateOrderInfo(OrderInfo orderInfo) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> getPaymentType(Long id) {
        return setResultError("系统正忙，请稍后再试");
    }
}
