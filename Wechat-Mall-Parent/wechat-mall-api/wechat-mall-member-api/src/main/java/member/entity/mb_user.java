package member.entity;

import com.lpc.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lin
 * @Date 2019/12/7
 *
 * 会员表
 */
@Data
public class mb_user extends BaseEntity implements Serializable {

    private String username;
    private String password;
    private String phone;
    private String email;
    private String address;
    private String openID;
}
