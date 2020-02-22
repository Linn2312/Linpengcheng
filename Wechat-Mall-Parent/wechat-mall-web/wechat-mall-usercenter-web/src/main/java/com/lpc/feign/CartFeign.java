package com.lpc.feign;

import cart.service.api.CartService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Lin
 * @Date 2020/1/5
 */
@FeignClient(name = "cart-server",fallback = CartHystrixFallBack.class,path = "/cart")
public interface CartFeign extends CartService {
}
