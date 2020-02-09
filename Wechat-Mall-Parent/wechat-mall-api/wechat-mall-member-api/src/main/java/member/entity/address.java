package member.entity;

import com.lpc.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lin
 * @Date 2020/1/26
 *
 * 用户地址表
 */
@Data
public class address extends BaseEntity implements Serializable {
    private Long userId;    //买家的id
    private String name;    //收货方姓名
    private String phone;   //收货方电话
    private String address; //收货方地址
}
