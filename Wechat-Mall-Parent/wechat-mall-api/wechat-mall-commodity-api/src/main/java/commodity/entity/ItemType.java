package commodity.entity;

import com.lpc.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LPC
 *
 * 商品类型表
 */
@Data
public class ItemType extends BaseEntity implements Serializable {
	/**
	 * 类目名称
	 */
	private String name;
	/**
	 * 状态。可选值:1(正常),2(删除)
	 */
	private Byte status;

}
