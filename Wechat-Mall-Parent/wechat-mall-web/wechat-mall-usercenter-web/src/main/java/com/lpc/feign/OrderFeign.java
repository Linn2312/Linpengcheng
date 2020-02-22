package com.lpc.feign;

import order.service.api.OrderService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Lin
 * @Date 2020/1/17
 *
 * hystrix:当网络不通时返回默认的配置数据
 */
@FeignClient(name = "order-server",fallback = OrderHystrixFallBack.class,path = "/order")
public interface OrderFeign extends OrderService {
}
