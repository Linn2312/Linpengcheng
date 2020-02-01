package commodity.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author LPC
 *
 * 商品表
 */

@Data
@Document(indexName = "",type = "")
public class Item{
	/**
	 * 主键
	 */
	@Id
	private Long id;
	/**
	 * 标题
	 */

	private String title;
	/**
	 * 商品价格
	 */
	private BigInteger price;
	/**
	 * 商品数量
	 */
	private Integer num;
	/**
	 * 图片地址
	 */
	private String image;
	/**
	 * 所属类型id
	 */
	private Long typeId;
    /**
     * 商品状态，1-正常，2-下架，3-删除
     */
	private Byte status;
	/**
	 * 商品名称
	 */
	private String typeName;
	/**
	 * 创建时间
	 */
	private Timestamp createdTime;

	/**
	 * 修改时间
	 */
	private Timestamp updatedTime;


	/**
	 * 购买数量
	 */
	private Long shoppingnum;
	/**
	 * 商品支付状态 0未付款 1已付款
	 */
	private Byte orderStatus;
	/**
	 * 商品所在的订单id
	 */
	private Long orderId;


}
