package com.lpc.utils;

import com.lpc.constants.BaseResponseConstants;

import java.util.Map;

public class ResultUtils {

	public static Object getResultMap(Map<String, Object> resultItemDesc) {
		Integer code = (Integer) resultItemDesc.get(BaseResponseConstants.HTTP_RESP_CODE_NAME);
		if (code.equals(BaseResponseConstants.HTTP_RESP_CODE_200)) {
			return resultItemDesc.get("data");
		}
		return null;
	}
}
