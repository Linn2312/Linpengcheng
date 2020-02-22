package com.lpc.controller;

import com.alibaba.fastjson.JSONObject;
import com.lpc.constants.BaseResponseConstants;
import com.lpc.constants.Constants;
import com.lpc.feign.pay.PaymentInfoFeign;
import com.lpc.service.pay.impl.PayImplService;
import com.lpc.utils.ControllerUtils;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
public class PayController{
	private static final String ERROR = "error";
	@Autowired
	private PaymentInfoFeign paymentInfoFeign;
	@Autowired
	private PayImplService payService;

	@RequestMapping("/addPaymentInfo")
	public String addPaymentInfo(HttpServletResponse response,
								 HttpServletRequest request,
								 @RequestParam("orderId")String orderId,
								 @RequestParam("totalPrice")String totalPrice){
		//判断该订单是否已经存在于支付订单里
		String pay_token = CookieUtil.getUid(request, Constants.PAY_TOKEN);
		if (StringUtils.isNotBlank(pay_token)){
			return "redirect:payGateway?token="+pay_token;
		}
		//第一次产生支付订单
		//接收到的是货币形式 转为数字形式
		String amount = ControllerUtils.convertToNumber(totalPrice);
		String id = ControllerUtils.convertToNumber(orderId);

		Map<String, Object> map = paymentInfoFeign.addPaymentInfo(id,amount);
		if (map.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_500)){
			return ControllerUtils.setError(request,(String) map.get(BaseResponseConstants.HTTP_RESP_CODE_MSG),ERROR);
		}
		String token = (String) ResultUtils.getResultMap(map);
		if (StringUtils.isEmpty(token)){
			return ControllerUtils.setError(request,"生成支付订单失败，请稍后重试",ERROR);
		}
		CookieUtil.addCookie(response, Constants.PAY_TOKEN,token,Constants.WEB_PAY_COOKIE_MAX_AGE);
		return "redirect:payGateway?token="+token;
	}

	@RequestMapping("/payGateway")
	public void payGateway(@RequestParam("token") String token, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html;charset=utf-8");
		Map<String, Object> payInfoToken = paymentInfoFeign.getPayInfoByToken(token);
		if (payInfoToken.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_500)){
			resp.getWriter().write("系统正忙，请稍后再试");
			return ;
		}
		Map<String, Object> resultMap = (Map<String, Object>) ResultUtils.getResultMap(payInfoToken);
		PrintWriter out=resp.getWriter();
		String json = new JSONObject().toJSONString(resultMap);
		PaymentInfo paymentInfo = new JSONObject().parseObject(json, PaymentInfo.class);
		String html = payService.pay(paymentInfo);
		out.println(html);
		out.close();
	}
}
