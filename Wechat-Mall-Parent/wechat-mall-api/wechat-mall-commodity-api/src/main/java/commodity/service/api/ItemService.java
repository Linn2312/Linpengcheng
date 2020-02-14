package commodity.service.api;

import commodity.entity.Item;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 功能说明:商品服务
 * @author LPC
 */
public interface ItemService {
	/**
	 * 首页展示商品
	 * @return
	 */
	@RequestMapping("/getItems")
    Map<String, Object> getItems();

	/**
	 * 点击查看更多商品
	 */
	@RequestMapping("/getAllItems")
	Map<String, Object> getAllItems();

	/**
	 * 查询商品
	 * @return
	 */
	@RequestMapping("/getItem")
    Map<String, Object> getItem(@RequestParam("id") Long id);

	/**
	 * 根据多个商品id 查询
	 */
	@RequestMapping("/selectItemsByIds")
	List<Item> selectItemsByIds(@RequestParam("ids") List<Long> ids);

	/**
	 * 查询配件
	 */
	@RequestMapping("/getAllParts")
    Map<String, Object> getAllParts();

	/**
	 * 搜索关键字
	 */
	@RequestMapping("/search")
	List<Item> search(@RequestParam("keywords")String keywords);

	/**
	 * 查看商品库存
	 */
	@RequestMapping("/selectNum")
    Integer selectNum(@RequestParam("itemId") String itemId);

	/**
	 * 更新商品库存
	 */
	@RequestMapping("/decreaseNum")
	Map<String, Object> decreaseNum(@RequestParam("orderId")String orderId);
}
