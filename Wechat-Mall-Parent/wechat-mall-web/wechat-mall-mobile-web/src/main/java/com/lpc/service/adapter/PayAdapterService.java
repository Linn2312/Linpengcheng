package com.lpc.service.adapter;


import pay.entity.PaymentInfo;
import pay.entity.PaymentType;

public interface PayAdapterService {
	String pay(PaymentInfo paymentInfo, PaymentType paymentType);
}
