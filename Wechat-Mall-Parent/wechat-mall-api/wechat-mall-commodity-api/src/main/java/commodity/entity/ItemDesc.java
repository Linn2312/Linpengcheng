package commodity.entity;

import com.lpc.entity.BaseEntity;
import lombok.Data;

/**
 * @author LPC
 *
 * 商品描述表
 */

@Data
public class ItemDesc extends BaseEntity {

	/**
	 * 商品描述
	 */
	private String itemDesc;

}
