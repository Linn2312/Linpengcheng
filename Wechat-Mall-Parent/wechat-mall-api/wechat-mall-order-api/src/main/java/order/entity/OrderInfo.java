package order.entity;

import com.lpc.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lin
 * @Date 2020/1/15
 *
 * 订单信息表
 */
@Data
public class OrderInfo extends BaseEntity implements Serializable {
    /**
     * 商户id
     */
    private Long userId;
    /**
     * 订单金额
     */
    private Double amount;
    /**
     * 订单状态 0未付款 1已付款 2已失效
     */
    private Byte status;

}
