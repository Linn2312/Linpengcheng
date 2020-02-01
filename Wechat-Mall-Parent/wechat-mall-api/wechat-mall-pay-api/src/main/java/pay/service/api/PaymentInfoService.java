package pay.service.api;

import order.entity.OrderInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pay.entity.PaymentInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Lin
 * @Date 2019/12/30
 */
public interface PaymentInfoService {
    /**
     * 创建支付信息并生成token
     * @return
     */
    @RequestMapping("/addPaymentInfo")
    Map<String, Object> addPaymentInfo(@RequestParam("orderId")String orderId,
                                       @RequestParam("totalPrice")String totalPrice);

    /**
     * 使用token查找支付信息
     * @return
     */
    @RequestMapping("/getPayInfoByToken")
    Map<String, Object> getPayInfoByToken(@RequestParam("token") String token);

    /**
     * 根据订单id获取订单信息
     * @return
     */
    @RequestMapping("/getPayInfoByOrderId")
    Map<String, Object> getPayInfoByOrderId(@RequestParam("orderId") String orderId);

    /**
     * 更新支付订单信息
     * @return
     */
    @RequestMapping("/updatePayInfo")
    Map<String, Object> updatePayInfo(@RequestBody PaymentInfo paymentInfo);

    /**
     * 更新商品订单信息
     */
    @RequestMapping("/updateOrderInfo")
    Map<String, Object> updateOrderInfo(@RequestBody OrderInfo orderInfo);
}
