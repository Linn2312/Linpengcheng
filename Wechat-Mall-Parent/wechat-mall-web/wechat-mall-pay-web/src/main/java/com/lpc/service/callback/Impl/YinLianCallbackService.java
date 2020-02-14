package com.lpc.service.callback.Impl;

import com.alibaba.fastjson.JSONObject;
import com.lpc.constants.Constants;
import com.lpc.feign.item.ItemFeign;
import com.lpc.feign.order.OrderFeign;
import com.lpc.feign.pay.PaymentInfoFeign;
import com.lpc.service.callback.CallbackService;
import com.lpc.utils.ResultUtils;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import lombok.extern.slf4j.Slf4j;
import order.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pay.entity.PaymentInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
@Service
public class YinLianCallbackService implements CallbackService {
	@Autowired
	private PaymentInfoFeign paymentInfoFeign;
	@Autowired
	private OrderFeign orderFeign;
	@Autowired
	private ItemFeign itemFeign;

	@Override
	public Map<String, String> syn(HttpServletRequest req) {
		LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");
		try {
			String encoding = req.getParameter(SDKConstants.param_encoding);
			Map<String, String> validateData = validateData(req, encoding);
			return validateData;
		} catch (Exception e) {
			log.error("#####YinLianCallbackService##syn() ERROR:", e);
			return null;
		} finally {
			LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");
		}

	}

	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public String asyn(HttpServletRequest req) {
		LogUtil.writeLog("BackRcvResponse接收后台通知开始");

		String encoding = req.getParameter(SDKConstants.param_encoding);
		Map<String, String> validateData = validateData(req, encoding);
		// 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(validateData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			// 验签失败，需解决验签问题
			return Constants.PAY_FAIL;
		}
		LogUtil.writeLog("验证签名结果[成功].");
		// 【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

		String orderId = validateData.get("orderId"); // 获取后台通知的数据，其他字段也可用类似方式获取
		Map<String, Object> byOrderIdPayInfoResult = paymentInfoFeign.getPayInfoByOrderId(orderId);
		if (byOrderIdPayInfoResult != null) {
			Map<String, Object> resultMap = (Map<String, Object>) ResultUtils.getResultMap(byOrderIdPayInfoResult);
			if (resultMap == null) {
				return Constants.PAY_FAIL;
			}
			String json = new JSONObject().toJSONString(resultMap);
			PaymentInfo paymentInfo = new JSONObject().parseObject(json, PaymentInfo.class);
			Integer state = paymentInfo.getState();
			if (state.equals(1)) {
				log.error("###asyn() 异步通知,订单id:{},状态已经为已支付,不能在修改为已支付.",orderId);
				return Constants.PAY_SUCCES;
			}
			paymentInfo.setState(1);
			paymentInfo.setPlantformorderId(validateData.get("queryId"));
			paymentInfo.setPayMessage(new JSONObject().toJSONString(validateData));
			paymentInfoFeign.updatePayInfo(paymentInfo); 	//更新支付表

			OrderInfo orderInfo = new OrderInfo();
			Byte status = 1;
			orderInfo.setStatus(status);
			orderInfo.setId(Long.parseLong(orderId));
			orderFeign.updateOrderInfo(orderInfo);				//更新订单表
			//更新商品库存
			itemFeign.decreaseNum(paymentInfo.getOrderId());
			log.info("###asyn() 异步通知,订单id:{},状态已经为已支付,修改成功。",orderId);
		}
		// 返回给银联服务器http 200 状态码
		return Constants.PAY_SUCCES;

	}
	private static Map<String, String> validateData(final HttpServletRequest req, String encoding){
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(req);

		LogUtil.printRequestLog(reqParam);

		Map<String, String> validateData = null;
		if (!reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			validateData = new HashMap<>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = e.getKey();
				String value = e.getValue();
				try {
					value = new String(value.getBytes(encoding), encoding);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				validateData.put(key, value);
			}
		}
		return validateData;
	}
	/**
	 * 获取请求参数中所有的信息
	 *
	 * @param request
	 * @return
	 */
	private static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (res.get(en) == null || "".equals(res.get(en))) {
					// System.out.println("======为空的字段名===="+en);
					res.remove(en);
				}
			}
		}
		return res;
	}

}
