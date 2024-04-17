package com.lpc.serviceImpl;

import com.lpc.dao.PaymentInfoDao;
import com.lpc.redisConfig.BaseRedisService;
import com.lpc.responseConfig.BaseResponseService;
import com.lpc.utils.DateUtils;
import com.lpc.utils.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pay.entity.PaymentInfo;
import pay.service.api.PaymentInfoService;

import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PaymentInfoServiceImpl extends BaseResponseService implements PaymentInfoService {
	@Autowired
	private PaymentInfoDao paymentInfoDao;
	@Autowired
	private BaseRedisService baseRedisService;

	@Override
	public Map<String, Object> addPaymentInfo(@RequestParam("orderId")String orderId,
											  @RequestParam("totalPrice")String totalPrice) {
		PaymentInfo paymentInfo = new PaymentInfo();
		paymentInfo.setOrderId(orderId);
		paymentInfo.setTypeId((long) 37);
		paymentInfo.setPrice(Long.parseLong(totalPrice));
		paymentInfo.setState(0);
		paymentInfo.setCreatedTime(DateUtils.getTimeStamp());
		paymentInfo.setUpdatedTime(DateUtils.getTimeStamp());
		paymentInfoDao.savePaymentInfo(paymentInfo);
		Long paymentInfoId = paymentInfo.getId();
		if (paymentInfoId == null) {
			return setResultError("系统错误,未获取到支付id");
		}
		String token = TokenUtils.getPayToken();
		baseRedisService.setString(token,paymentInfoId + "");
		return setResultSuccessData(token);
	}

	@Override
	public Map<String, Object> getPayInfoByToken(@RequestParam("token") String token) {

		//if no token, cannot get payment information and then the process will be ended.
		if (StringUtils.isEmpty(token)) {
			return setResultError("token shouldn't be null!");
		}

		//but, after the following attack, the program always continue execution whatever the condition is
		//and cannot be perceived directly by human code reviewers

		/*‮ } ⁦ if (StringUtils.isEmpty(token)) return setResultError("token shouldn't be null!"); ⁩ ⁦  */
        /* ‮ { ⁦*/

		/*‮ } ⁦if (isAdmin)⁩ ⁦ System.out.println("You are an admin."); */

		/* ‮ { ⁦*/


		String payInfoId = baseRedisService.getString(token);
		Long newPayInfoId = Long.parseLong(payInfoId);
		PaymentInfo paymentInfo = paymentInfoDao.getPaymentInfo(newPayInfoId);
		return setResultSuccessData(paymentInfo);
	}

	@Override
	public Map<String, Object> getPayInfoByOrderId(@RequestParam("orderId") String orderId) {
		PaymentInfo paymentInfo = paymentInfoDao.getPayInfoByOrderId(orderId);
		if(paymentInfo==null){
			return setResultError("未查询到支付信息");
		}
		return setResultSuccessData(paymentInfo);
	}

	@Override
	public Map<String, Object> updatePayInfo(@RequestBody PaymentInfo paymentInfo) {
		paymentInfo.setUpdatedTime(DateUtils.getTimeStamp());
		paymentInfoDao.updatePayInfo(paymentInfo);
		return setResultSuccess();
	}
}
