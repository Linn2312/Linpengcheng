package com.lpc.schedule;

import com.lpc.base.BaseController;
import com.lpc.feign.order.OrderFeign;
import com.lpc.feign.pay.PaymentInfoFeign;
import com.lpc.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import order.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pay.entity.PaymentInfo;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Lin
 * @Date 2020/1/23
 *
 * 更新订单状态定时任务 每隔1min 查询数据库全部未付款订单  付款有效期是15mins
 */
@Component
@Slf4j
public class OrderSchedule extends BaseController {
    @Autowired
    private OrderFeign orderFeign;
    @Autowired
    private PaymentInfoFeign paymentInfoFeign;

    @Scheduled(cron = "0 0/1 * * * ?")  //每1分钟执行一次
    @Async
    public void order(){
        log.info("#######################OrderScdule类执行order()方法########################");
        //查询数据库全部未付款订单
        List<OrderInfo> list = orderFeign.selectAllOrders();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (OrderInfo orderInfo : list) {  //先将Timestamp类型转为字符串 再转为long
            long systemTime = System.currentTimeMillis();
            String createdTime = df.format(orderInfo.getCreatedTime());
            String now = df.format(systemTime);
            long D_value = DateUtils.fromDateStringToLong(now) - DateUtils.fromDateStringToLong(createdTime);
            if (orderInfo.getStatus() == 0 && D_value >= 1000*60*15L){  //订单状态为未付款并且未付款时间大于15分钟
                //更新订单状态为已失效
                Byte status = 2;
                orderFeign.updateOrderStatus(orderInfo.getId(),status);
                PaymentInfo paymentInfo = new PaymentInfo();
                paymentInfo.setState(2);
                paymentInfo.setOrderId(orderInfo.getId()+"");
                paymentInfoFeign.updatePayInfo(paymentInfo);
            }
        }
        log.info("#######################order()方法执行完毕########################");
    }

}
