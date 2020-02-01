package member.service.api;

import member.entity.address;
import member.entity.mb_user;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Lin
 * @Date 2019/12/7
 *
 * 会员服务接口
 */
public interface UserService {
    /**
     * 会员注册服务
     * 得判断用户名、手机号、邮箱是否已经被注册
     * @param mb_user 采用@RequestBody注解将请求体中的json字符串绑定到mb_user对象上
     * @return
     */
    @PostMapping("/register")
    Map<String, Object> register(@RequestBody mb_user mb_user);

    /**
     * 会员登录服务
     * 思路：登录成功后，随机产生token，作为redis的key，userID作为value。返回token给客户端
     * @param mb_user
     * @return
     */
    @PostMapping("/login")
    Map<String, Object> login(@RequestBody mb_user mb_user);

    /**
     * 根据token获取redis中的用户id，再进入数据库查询用户信息
     * @param token
     * @return
     */
    @PostMapping("/getUserInfo")
    Map<String, Object> getUserInfo(@RequestParam("token") String token);

    /**
     * 根据得到的openID查询数据库中用户信息
     * @param openID
     * @return
     */
    @PostMapping("/getUserLoginOpenID")
    Map<String, Object> getUserByLoginOpenID(@RequestParam("openID") String openID);

    /**
     * 根据用户邮箱找回密码
     * 发送密码到该邮箱
     */
    @RequestMapping("/findPassword")
    Map<String, Object> findPassword(@RequestParam("email") String email);

    /**
     * 修改密码
     * @param phone
     * @param password
     */
    @RequestMapping("/updatePwd")
    Map<String, Object> updatePwd(@RequestParam("phone") String phone,@RequestParam("password") String password);

    /**
     * 添加收获地址
     * @param address
     * @return
     */
    @RequestMapping("/addAddress")
    Map<String, Object> addAddress(address address);
}
