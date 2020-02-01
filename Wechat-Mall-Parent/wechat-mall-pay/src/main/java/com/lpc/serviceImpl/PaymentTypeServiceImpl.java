package com.lpc.serviceImpl;

import com.lpc.dao.PaymentTypeDao;
import com.lpc.responseConfig.BaseResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pay.entity.PaymentType;
import pay.service.api.PaymentTypeService;

import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PaymentTypeServiceImpl extends BaseResponseService implements PaymentTypeService {
	@Autowired
	private PaymentTypeDao paymentTypeDao;

	@RequestMapping("/getPaymentType")
	public Map<String, Object> getPaymentType(@RequestParam("id") Long id) {
		PaymentType paymentType = paymentTypeDao.getPaymentType(id);
		if (paymentType == null) {
			return setResultError("未查找到类型");
		}
		return setResultSuccessData(paymentType);
	}

}
