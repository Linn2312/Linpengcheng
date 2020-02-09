package order.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Lin
 * @Date 2020/1/20
 *
 * 用于接收解析前台传来的json字符串之后的实体类
 */
@Data
public class OrderVo implements Serializable {
    /**
     * 购物车选中的商品的id
     */
    private List<Long> itemIds;
    /**
     * 每个商品的购买数量
     */
    private List<Long> shoppingnums;
    /**
     * 总价钱
     */
    private Double amount;
}
