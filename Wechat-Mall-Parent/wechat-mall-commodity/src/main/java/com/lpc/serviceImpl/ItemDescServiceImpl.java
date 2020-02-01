package com.lpc.serviceImpl;

import com.lpc.dao.ItemDescDao;
import com.lpc.responseConfig.BaseResponseService;
import commodity.entity.ItemDesc;
import commodity.service.api.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/commodity")
public class ItemDescServiceImpl extends BaseResponseService implements ItemDescService {
	@Autowired
	private ItemDescDao itemDescDao;

	public Map<String, Object> getItemDesc(@RequestParam("id") Long id) {
		ItemDesc itemDesc = itemDescDao.getItemDesc(id);
		return setResultSuccessData(itemDesc);
	}

}
