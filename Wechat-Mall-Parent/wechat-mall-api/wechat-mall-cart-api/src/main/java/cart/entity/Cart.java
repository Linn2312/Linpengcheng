package cart.entity;

import com.lpc.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lin
 * @Date 2020/1/5
 *
 * 购物车表
 */
@Data
public class Cart extends BaseEntity implements Serializable {
    private Long userId;
    private Long itemId;
}
