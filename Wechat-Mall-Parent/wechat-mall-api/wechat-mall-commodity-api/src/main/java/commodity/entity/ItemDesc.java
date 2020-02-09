package commodity.entity;

import com.lpc.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LPC
 *
 * 商品描述表
 */

@Data
public class ItemDesc extends BaseEntity implements Serializable {

	/**
	 * 商品描述
	 */
	private String itemDesc;

}
