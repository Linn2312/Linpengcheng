package com.lpc.serviceImpl;

import com.lpc.constants.Constants;
import com.lpc.constants.DBTable;
import com.lpc.constants.Mail;
import com.lpc.redisConfig.BaseRedisService;
import com.lpc.responseConfig.BaseResponseService;
import com.lpc.utils.DateUtils;
import com.lpc.utils.MD5Utils;
import com.lpc.utils.TokenUtils;
import com.alibaba.fastjson.JSONObject;
import com.lpc.dao.UserDao;
import com.lpc.mailBoxProducer.MailBoxProducer;
import lombok.extern.slf4j.Slf4j;
import member.entity.address;
import member.entity.mb_user;
import member.service.api.UserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import java.util.Map;

/**
 * @author Lin
 * @Date 2019/12/7
 *
 */
@RestController
@Slf4j
@RequestMapping("/member")
public class UserServiceImpl extends BaseResponseService implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private BaseRedisService baseRedisService;
    @Autowired
    private MailBoxProducer mailBoxProducer;
    @Value("${wechat-shop-msg-register}")
    private String WECHAT_SHOP_MSG_MESSAGE;   //注册用户的队列
    @Value("${wechat-shop-msg-findPwd}")
    private String WECHAT_SHOP_MSG_FINDPWD; //找回密码的队列

    @Override
    public Map<String, Object> register(@RequestBody mb_user mb_user) {
        String password = mb_user.getPassword();
        String phone = mb_user.getPhone();
        try {
            //先判断
            mb_user m1 = userDao.selectUserByName(mb_user.getUsername());
            if (m1!=null) return setResultError("用户名已经被注册了");
            mb_user m2 = userDao.selectUserByEmail(mb_user.getEmail());
            if (m2!=null) return setResultError("邮箱已经被注册过了");
            mb_user m3 = userDao.selectUserByPhone(mb_user.getPhone());
            if (m3!=null) return setResultError("手机号已经被注册过了");
            //开始注册
            //设置时间
            mb_user.setCreatedTime(DateUtils.getTimeStamp());
            mb_user.setUpdatedTime(DateUtils.getTimeStamp());
            //密码加盐加密 手机号作为盐值
            String new_password = this.setMD5SaltPassword(password,phone);
            mb_user.setPassword(new_password);
            //开始UserDao的注册服务
            userDao.save(mb_user, DBTable.TABLE_MB_USER);

            /////注册成功后要开始发送消息给MQ
            //创建队列
            Destination destination = new ActiveMQQueue(WECHAT_SHOP_MSG_MESSAGE);
            //组装报文消息 json格式
            String msg = mailMessages(mb_user.getEmail(), mb_user.getUsername());
            //打印日志
            log.info("###register() 注册，发送邮件报文mailMessage " , msg);
            //开始发送到MQ
            mailBoxProducer.send(destination,msg);

            return setResultSuccess();
        }catch (Exception e){
            log.error("### register() ERROR:" , e);
            return setResultError("系统正忙，请稍后重试");
        }
    }

    @Override
    public Map<String, Object> login(@RequestBody mb_user mb_user) {
        //开始登录
        //进数据库查
        //第一次登录成功后生成对应的token,将token作为key,userID作为value存入redis中(token是TokenUtils工具类随机产生的一串字符串)
        //生成并返回token

        //
        String password = mb_user.getPassword();
        String phone = mb_user.getPhone();
        String new_password = this.setMD5SaltPassword(password,phone);
        mb_user mb_user1 = userDao.getUserPhoneAndPsw(new_password,phone);
        if (mb_user1 == null){
            return setResultError("账号或密码错误！");
        }

        //通过openID关联已有账户
        String openID = mb_user1.getOpenID();
        if (StringUtils.isEmpty(openID)){
            //修改数据库的openID值
            userDao.updateUserOpenID(mb_user.getOpenID(), DateUtils.getTimeStamp(), mb_user1.getId());
        }

        //
        Long userId = mb_user1.getId();
        String token = setTokenRedis(userId);
        log.info("###### 登录  token存入redis: token：{}  userId：{}",token,userId);
        //
        return setResultSuccessData(token);
    }
    @Override
    public Map<String, Object> getUserInfo(@RequestParam("token") String token) {
        if (StringUtils.isEmpty(token)){
            return setParametersError("token不能为空");
        }
        //从redis中查找userID
        String userId = baseRedisService.getString(token);
        if (StringUtils.isEmpty(userId)){
            setResultError("用户信息已过期！");
        }
        //进入数据库查询
        Long id = Long.parseLong(userId);
        mb_user userInfo = userDao.getUserById(id);
        userInfo.setPassword("");   //密码一定要置空，不能让后台看见
        return setResultSuccessData(userInfo);
    }

    @Override
    public Map<String, Object> getUserByLoginOpenID(@RequestParam("openID") String openID) {
        //根据openID查询数据库用户信息
        mb_user mb_user = userDao.getUserByLoginOpenID(openID);
        if (mb_user == null){
            return setResultError("没有关联用户!");
        }
        //有关联 自动登录：获取token 存redis，存cookie
        Long userId = mb_user.getId();
        String token = setTokenRedis(userId);
        log.info("###### 登录  token存入redis: token：{}  userId：{}",token,userId);
        //
        return setResultSuccessData(token);
    }

    @Override
    public Map<String, Object> findPassword(@RequestParam("email")String email) {
        //创建队列
        Destination destination = new ActiveMQQueue(WECHAT_SHOP_MSG_FINDPWD);
        //组装报文消息 json格式
        String msg = mailMessages(email, null);
        //打印日志
        log.info("###findPassword() 找回密码，发送邮件报文mailMessage " , msg);
        //开始发送到MQ
        mailBoxProducer.send(destination,msg);
        return setResultSuccess();
    }

    @Override
    public Map<String, Object> updatePwd(@RequestParam("phone") String phone,@RequestParam("password") String password) {
        mb_user mb_user = new mb_user();
        mb_user.setPassword(this.setMD5SaltPassword(password, phone));
        mb_user.setUpdatedTime(DateUtils.getTimeStamp());
        mb_user.setPhone(phone);
        int i = userDao.updatePwd(mb_user);
        if (i!=1){
            return setResultError();
        }
        return setResultSuccess("修改密码成功");
    }

    @Override
    public Map<String, Object> addAddress(address address) {
        userDao.save(address,DBTable.ADDRESS);
        return setResultSuccess("添加地址成功");
    }

    /**
     * 重构代码  登录成功后<token,userID>存入redis
     */
    private String setTokenRedis(Long userId){
        String token = TokenUtils.getToken();
        baseRedisService.setString(token,userId+"", Constants.REDIS_TIME_OUT);
        return token;
    }

    /**
     * 密码加密 手机号作盐值
     */
    private String setMD5SaltPassword(String password, String phone) {
        return MD5Utils.md5(password+phone);
    }

    /**
     * 组装报文的方法
     *
     * 报文格式
     *  {
     *      "header":{"interfaceType":"sms_mail"},
     *      "content":{
     *          "email":"690699570@qq.com",
     *          "userName":"lpc"
     *      }
     *  }
     */
    private String mailMessages(String email, String userName){
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        JSONObject content = new JSONObject();
        header.put("interfaceType", Mail.SMS_MAIL);
        content.put("email",email);
        content.put("userName",userName);
        root.put("header",header);
        root.put("content",content);
        return root.toJSONString();
    }
}
