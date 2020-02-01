package com.lpc.controller.item;

import com.lpc.constants.BaseResponseConstants;
import com.lpc.controller.base.BaseController;
import com.lpc.feign.item.ItemDescFeign;
import com.lpc.feign.item.ItemFeign;
import com.lpc.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 商品详情
 * @author LPC
 */
@Slf4j
@Controller
public class ItemDescController extends BaseController {
	private static final String ERROR = "error";
	private static final String ITEMDESC = "itemDesc";
	@Autowired
	private ItemDescFeign itemDescFeign;
	@Autowired
	private ItemFeign itemFeign;

	/**
	 * 商品详情
	 */
	@RequestMapping("/itemDesc")
	public String itemDesc(HttpServletRequest request,@RequestParam("id") Long id) {
		//根据id查询商品信息
		Map<String, Object> resultItem = itemFeign.getItem(id);
		if (resultItem.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_500)){
			return super.setError(request,(String)resultItem.get(BaseResponseConstants.HTTP_RESP_CODE_MSG),ERROR);
		}
		Map<String, Object> item = (Map<String, Object>) ResultUtils.getResultMap(resultItem);
		request.setAttribute("item", item);

		//根据id查询商品描述 商品id和商品详情id是一一对应的
		Map<String, Object> resultItemDesc = itemDescFeign.getItemDesc(id);
		if (resultItemDesc.get(BaseResponseConstants.HTTP_RESP_CODE_NAME).equals(BaseResponseConstants.HTTP_RESP_CODE_500)){
			return super.setError(request,(String)resultItemDesc.get(BaseResponseConstants.HTTP_RESP_CODE_MSG),ERROR);
		}
		Map<String, Object> itemDesc = (Map<String, Object>) ResultUtils.getResultMap(resultItemDesc);
		request.setAttribute("itemDesc", itemDesc);
		return ITEMDESC;
	}
}
