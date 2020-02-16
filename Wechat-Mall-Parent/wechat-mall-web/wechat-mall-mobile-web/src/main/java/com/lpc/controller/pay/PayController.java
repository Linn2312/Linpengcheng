package com.lpc.controller.pay;

import com.lpc.constants.BaseResponseConstants;
import com.lpc.constants.Constants;
import com.lpc.controller.base.BaseController;
import com.lpc.feign.pay.PaymentInfoFeign;
import com.lpc.utils.CookieUtil;
import com.lpc.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pay.entity.PaymentInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class PayController extends BaseController {
	private static final String ERROR = "error";
	@Autowired
	private PaymentInfoFeign paymentInfoFeign;

	@RequestMapping("/addPaymentInfo")
	public String addPaymentInfo(HttpServletResponse response,
								 HttpServletRequest request,
								 @RequestParam("orderId")String orderId,
								 @RequestParam("totalPrice")String totalPrice){
		//先判断该订单是否已经存在于支付订单里
		String pay_token = CookieUtil.getUid(request, Constants.PAY_TOKEN);
		if (StringUtils.isNotBlank(pay_token)){
			return "redirect:http://28ii870524.zicp.vip/pay-web/payGateway?token="+pay_token;
		}
		////如果pay_token不存在则分两种情况 1.支付信息已失效 2.还未生成支付信息
		//第一种情况 需返回提示给用户
		Map<String, Object> paymentInfo = paymentInfoFeign.getPayInfoByOrderId(orderId);
		if (paymentInfo.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_500)){
			return super.setError(request,(String) paymentInfo.get(BaseResponseConstants.HTTP_RESP_CODE_MSG),ERROR);
		}
		PaymentInfo p = (PaymentInfo) paymentInfo.get(BaseResponseConstants.HTTP_RESP_CODE_DATA);
		if (p.getState()==2){
			return super.setError(request,"此订单已失效，请重新购买",ERROR);
		}
		//第二种情况
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
		CookieUtil.addCookie(response, Constants.PAY_TOKEN,token,Constants.WEB_PAY_COOKIE_MAX_AGE);
		return "redirect:http://28ii870524.zicp.vip/pay-web/payGateway?token="+token;
	}
}
