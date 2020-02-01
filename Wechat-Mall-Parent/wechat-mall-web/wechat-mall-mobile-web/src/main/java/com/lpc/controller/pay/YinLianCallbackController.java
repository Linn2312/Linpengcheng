package com.lpc.controller.pay;

import com.lpc.controller.base.BaseController;
import com.lpc.feign.cart.CartFeign;
import com.lpc.service.callback.CallbackService;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/YinLianPay/callback")
@Controller
@Slf4j
public class YinLianCallbackController extends BaseController {
	private static final String PAY_SUCCESS = "pay_success";
	private static final String PAY_FAIL = "pay_fail";
	@Autowired
	private CallbackService callbackService;

	/**
	 * 功能说明:同步回调
	 * 返回给商户到前台页面  不更新订单状态
	 * @return
	 */
	@RequestMapping("/syn")
	public String syn(HttpServletRequest request) {
		Map<String, String> synResultMap = callbackService.syn(request);

		String encoding = request.getParameter(SDKConstants.param_encoding);
		if (!AcpService.validate(synResultMap, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			request.setAttribute("orderId", Long.parseLong(synResultMap.get("orderId")));
			return PAY_FAIL;
		}
		request.setAttribute("txnAmt", Double.parseDouble(synResultMap.get("txnAmt")));
		request.setAttribute("orderId", Long.parseLong(synResultMap.get("orderId")));
		LogUtil.writeLog("验证签名结果[成功].");
		return PAY_SUCCESS;
	}

	/**
	 * 功能说明:异步回调
	 * 更新订单状态 生成支付报文什么的
	 * @return
	 */
	@RequestMapping("/asyn")
	public String asyn(HttpServletRequest request) {
		return callbackService.asyn(request);
	}
}
