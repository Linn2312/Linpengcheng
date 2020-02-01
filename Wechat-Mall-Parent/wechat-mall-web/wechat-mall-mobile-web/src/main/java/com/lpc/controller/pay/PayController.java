package com.lpc.controller.pay;

import com.lpc.constants.BaseResponseConstants;
import com.lpc.controller.base.BaseController;
import com.lpc.feign.pay.PaymentInfoFeign;
import com.lpc.service.pay.impl.PayImplService;
import com.lpc.utils.ResultUtils;
import com.alibaba.fastjson.JSONObject;
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
public class PayController extends BaseController {
	private static final String ERROR = "error";
	@Autowired
	private PaymentInfoFeign paymentInfoFeign;
	@Autowired
	private PayImplService payService;

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
