package com.lpc.feign;

import org.springframework.cloud.openfeign.FeignClient;
import pay.service.api.PaymentTypeService;

@FeignClient(name = "pay-server",fallback = PaymentHystrixFallBack.class,path = "/pay")
public interface PaymentTypeFeign extends PaymentTypeService {

}
