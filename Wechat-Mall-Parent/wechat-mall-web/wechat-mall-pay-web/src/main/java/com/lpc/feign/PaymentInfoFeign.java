package com.lpc.feign;

import org.springframework.cloud.openfeign.FeignClient;
import pay.service.api.PaymentInfoService;

@FeignClient(name = "pay-server",fallback = PaymentHystrixFallBack.class,path = "/pay")
public interface PaymentInfoFeign extends PaymentInfoService {

}
