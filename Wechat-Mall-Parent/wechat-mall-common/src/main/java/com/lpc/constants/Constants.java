package com.lpc.constants;

/**
 * @author Lin
 * @Date 2019/12/16
 */
public interface Constants {
    //设置用户登录的redis缓存过期时间为10天
    long REDIS_TIME_OUT = 60*60*24*30L;
    //user-token
    String USER_TOKEN = "user_token";
    //pay-token
    String PAY_TOKEN = "pay_token";
    //cookie过期时间 10天  秒为单位
    int WEB_USER_COOKIE_MAX_AGE = 60*60*24*30;
    //设置支付token的cookie有效期 15分钟 秒为单位
    int WEB_PAY_COOKIE_MAX_AGE = 60*15;
    //qq授权 openID
    String USER_OPENID = "openID";
    //设置购物车的redis缓存有效期 30天 秒为单位
    Long USER_SHOP_TERMOFVALIDITY = 60*60*24*30L;
    //支付成功
    String PAY_SUCCES = "ok";
    //支付失败
    String PAY_FAIL = "fail";
    //用户点击邮箱中的链接进行修改密码
    String UPDATEPASSWORD = "http://28ii870524.zicp.vip/mobile-web/toUpdatePwd";
}
