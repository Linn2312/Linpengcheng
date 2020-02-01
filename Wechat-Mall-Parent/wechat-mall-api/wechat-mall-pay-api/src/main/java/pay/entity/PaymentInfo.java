package pay.entity;

import com.lpc.entity.BaseEntity;
import lombok.Data;

/**
 * 支付信息表
 */
@Data
public class PaymentInfo extends BaseEntity {

	/**
	 * 支付类型id
	 */
	private Long typeId;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 第三方平台支付id
	 */
	private String plantformorderId;
	/**
	 * 订单总价格 以分为单位
	 */
	private Long price;
	/**
	 * 支付状态 0未支付 1已支付2已失效
	 */
	private Integer state;
	/**
	 * 支付报文
	 */
	private String payMessage;

}
