package com.lpc.serviceImpl;

import com.lpc.dao.ItemDao;
import com.lpc.dao.ItemRepository;
import com.lpc.dao.ItemTypeDao;
import com.lpc.responseConfig.BaseResponseService;
import com.lpc.utils.ReflectionUtils;
import commodity.entity.Item;
import commodity.entity.ItemType;
import commodity.service.api.ItemService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commodity")
public class ItemServiceImpl extends BaseResponseService implements ItemService {
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private ItemTypeDao itemTypeDao;
	@Autowired
	private ItemRepository itemRepository;

	@Override
	public Map<String, Object> getItems() {
		// 查询前两种类型,每查询出来一个就进数据库查询对应的全部商品信息，存入map
		List<ItemType> listItemType = itemTypeDao.getTwoItemsType();
		return this.get(listItemType);
	}

	@Override
	public Map<String, Object> getAllItems() {
		// 查询前两种类型,每查询出来一个就进数据库查询对应的全部商品信息，存入map
		List<ItemType> listItemType = itemTypeDao.getShowMoreItemsType();
		return this.get(listItemType);
	}

	@Override
	public Map<String, Object> getAllParts() {
		// 查询类型为配件的商品
		List<ItemType> listItemType = itemTypeDao.getPartType();
		return this.get(listItemType);
	}

	@Override
	public List<Item> search(@RequestParam("keywords")String keywords) {
		// 构建查询条件
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		// 添加基本分词查询 matchQuery
		queryBuilder.withQuery(QueryBuilders.matchQuery("title", keywords));
		// 搜索，获取结果
		Page<Item> search = itemRepository.search(queryBuilder.build());
		return search.getContent();
	}

	@Override
	public Map<String, Object> getItem(@RequestParam("id") Long id) {
		Item item = itemDao.getItem(id);
		if(item == null){
			return setResultError("没有查询到结果");
		}
		return setResultSuccessData(item);
	}

	@Override
	public List<Item> selectItemsByIds(@RequestParam("ids")List<Long> ids) {
		List<Item> list = new ArrayList<>();
		for (Long id : ids) {
			Item item = itemDao.getItem(id);
			list.add(item);
		}
		return list;
	}

	//反射 获取get方法
	private Map<String, Object> get(List list){
		Map<String, Object> result = new HashMap<>();
		for (Object o : list) {
			Long id = (Long) ReflectionUtils.getGetMethod(o,"id");
			String name = (String)ReflectionUtils.getGetMethod(o,"name");
			List<Item> listItem = itemDao.getAllItems(id);
			if (listItem.size() > 0) {
				//商品类目name为key，对应的全部商品信息为value
				//result:{"name1":listItem1,"name2":listItem2....}
				result.put(name,listItem);
			}
		}
		return setResultSuccessData(result);
	}
}
