package order.entity;

import com.lpc.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lin
 * @Date 2020/1/15
 *
 * 订单详情表
 */
@Data
public class OrderDetail extends BaseEntity implements Serializable {
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 商品id
     */
    private Long itemId;
    /**
     * 购买数量
     */
    private Long shoppingnum;

}
