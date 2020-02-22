package order.service.api;

import order.entity.OrderInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author Lin
 * @Date 2020/1/15
 *
 * 订单服务接口
 */
public interface OrderService {
    /**
     * 添加订单信息
     * 根据用户id和总金额
     * 有效期15分钟 如果付了款 则永久保留
     */
    @RequestMapping("/addOrderInfo")
    Map<String,Object> addOrderInfo(@RequestParam("userId")Long userId,@RequestParam("amount")Double amount);

    /**
     * 添加订单详情信息
     * 一个订单有多个商品 就得多个订单详情表进行容纳一个订单
     */
    @RequestMapping(value = "/addOrderDetail")
    Map<String,Object> addOrderDetail(@RequestParam("orderId")Long orderId, @RequestParam("itemId")Long itemId, @RequestParam("shoppingnum")Long shoppingnum);

    /**
     * 修改订单状态 清空过期时间
     * 根据订单id
     */
    @RequestMapping("/updateOrderStatus")
    Map<String,Object> updateOrderStatus(@RequestParam("orderInfoId")Long orderInfoId,
                                         @RequestParam("status")Byte status);

    /**
     * 查询用户下的所有订单
     */
    @RequestMapping("/selectUserAllOrder")
    List<OrderInfo> selectUserAllOrder(@RequestParam("userId")Long userId);

    /**
     * 取消订单 直接删除该订单和订单详情
     */
    @RequestMapping("/cancelOrder")
    Map<String,Object> cancelOrder(@RequestParam("orderId") Long orderId);

    /**
     * 根据订单中商品的id
     */
    @RequestMapping("/getItemsIdByOrderId")
    List<Long> getItemsIdByOrderId(@RequestParam("orderId") Long orderId);

    /**
     * 根据订单id查询商品的购买数量
     */
    @RequestMapping("/getShoppingnum")
    List<Long> getShoppingnum(@RequestParam("orderId") Long orderId);

    /**
     * 查询用户订单的数量
     */
    @RequestMapping("/selectUserOrderCount")
    Integer selectUserOrderCount(@RequestParam("id") Long id);

    /**
     * 查询订单状态
     */
    @RequestMapping("/getOrderStatus")
    Byte getOrderStatus(@RequestParam("orderId")Long orderId);

    /**
     * 查询该用户所有未付款订单信息
     */
    @RequestMapping("/selectAllOrders")
    List<OrderInfo> selectAllOrders();

    /**
     * 更新商品订单信息
     */
    @RequestMapping("/updateOrderInfo")
    Map<String, Object> updateOrderInfo(@RequestBody OrderInfo orderInfo);
}
