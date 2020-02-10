package com.lpc.controller.pay;

import com.lpc.constants.BaseResponseConstants;
import com.lpc.controller.base.BaseController;
import com.lpc.feign.pay.PaymentInfoFeign;
import com.lpc.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class PayController extends BaseController {
	private static final String ERROR = "error";
	@Autowired
	private PaymentInfoFeign paymentInfoFeign;

	@RequestMapping("/addPaymentInfo")
	public String addPaymentInfo(HttpServletRequest request,
								 @RequestParam("orderId")String orderId,
								 @RequestParam("totalPrice")String totalPrice){
		//接收到的是货币形式 转为数字形式
		String amount = super.convertToNumber(totalPrice);
		String id = super.convertToNumber(orderId);

		Map<String, Object> map = paymentInfoFeign.addPaymentInfo(id,amount);
		if (map.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_500)){
			return super.setError(request,(String) map.get(BaseResponseConstants.HTTP_RESP_CODE_MSG),ERROR);
		}
		String token = (String) ResultUtils.getResultMap(map);
		if (StringUtils.isEmpty(token)){
			return super.setError(request,"生成支付订单失败，请稍后重试",ERROR);
		}
		return "redirect:http://28ii870524.zicp.vip/pay-web/payGateway?token="+token;
	}
}
