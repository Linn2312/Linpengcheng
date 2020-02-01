package com.lpc.adapter;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Lin
 * @Date 2019/12/10
 *
 * 所有消息都会交由其进行转发
 *
 * 父接口只规定一个方法，可以由很多消息，如邮件消息，短信消息等服务类进行实现重写
 */
public interface MessageAdapter {

    void distribute(JSONObject jsonObject);
}
