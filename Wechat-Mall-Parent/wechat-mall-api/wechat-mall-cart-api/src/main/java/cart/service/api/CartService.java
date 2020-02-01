package cart.service.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Lin
 * @Date 2020/1/5
 */
public interface CartService {
    /**
     * 查询指定用户的购物车
     */
    @RequestMapping("/selectCart")
    Map<String, Object> selectCart(@RequestParam("userId") String userId);

    /**
     * 添加指定用户的购物车
     * @return
     */
    @RequestMapping("/addCart")
    Map<String, Object> addCart(@RequestParam("userId") String userId, @RequestParam("itemId") String itemId);

    /**
     * 删除指定用户购物车的指定商品
     */
    @RequestMapping("/delCart")
    Map<String, Object> delCart(@RequestParam("userId") String userId, @RequestParam("itemId") String itemId);

    /**
     * 查询用户购物车中商品的数量
     */
    @RequestMapping("/selectUserCartCount")
    Integer selectUserCartCount(@RequestParam("id") Long id);
}
