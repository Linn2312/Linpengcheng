package com.lpc.controller;

import com.alibaba.fastjson.JSONObject;
import com.lpc.constants.BaseResponseConstants;
import com.lpc.feign.PaymentInfoFeign;
import com.lpc.service.pay.impl.PayImplService;
import com.lpc.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pay.entity.PaymentInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
public class PayController{
	@Autowired
	private PaymentInfoFeign paymentInfoFeign;
	@Autowired
	private PayImplService payService;

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
