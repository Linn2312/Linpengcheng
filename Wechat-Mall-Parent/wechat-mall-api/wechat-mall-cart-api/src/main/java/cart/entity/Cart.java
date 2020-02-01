package cart.entity;

import com.lpc.entity.BaseEntity;
import lombok.Data;

/**
 * @author Lin
 * @Date 2020/1/5
 *
 * 购物车表
 */
@Data
public class Cart extends BaseEntity {
    private Long userId;
    private Long itemId;
}
