package com.lpc.service.pay.impl;

import com.alibaba.fastjson.JSONObject;
import com.lpc.feign.pay.PaymentTypeFeign;
import com.lpc.service.adapter.Impl.YinLianPay;
import com.lpc.service.adapter.PayAdapterService;
import com.lpc.service.pay.PayService;
import com.lpc.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pay.entity.PaymentInfo;
import pay.entity.PaymentType;

import java.util.Map;

@Slf4j
@Service
public class PayImplService implements PayService {
	@Autowired
	private PaymentTypeFeign paymentTypeFeign;
	@Autowired
    private YinLianPay yinLianPay;
	@Override
	public String pay(PaymentInfo paymentInfo) {
		Long typeId = paymentInfo.getTypeId();
		Map<String, Object> paymentTypeMap = paymentTypeFeign.getPaymentType(typeId);
		if (paymentTypeMap == null) {
			log.error("####pay() typeId:{},paymentTypeMap is null");
			return null;
		}
		Map<String, Object> data = (Map<String, Object>) ResultUtils.getResultMap(paymentTypeMap);
		String json = new JSONObject().toJSONString(data);
		PaymentType paymentType = new JSONObject().parseObject(json, PaymentType.class);
		if (paymentType == null) {
			return null;
		}
		String typeName = paymentType.getTypeName();
		PayAdapterService payAdapterService = null;
		switch (typeName) {
		case "YinLianPay":
			payAdapterService = yinLianPay;
			break;
		default:
			break;
		}
		return payAdapterService.pay(paymentInfo, paymentType);
	}
}
