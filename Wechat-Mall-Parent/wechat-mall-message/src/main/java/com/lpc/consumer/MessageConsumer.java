package com.lpc.consumer;

import com.alibaba.fastjson.JSONObject;
import com.lpc.adapter.MessageAdapter;
import com.lpc.adapter.adapterImpl.FindPasswordImpl;
import com.lpc.adapter.adapterImpl.MailAdapterImpl;
import com.lpc.constants.Mail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author Lin
 * @Date 2019/12/10
 *
 * 消息消费者从mq获取消息并进行判断是哪种消息,然后交由适配器进行发送 队列名称是email-Queue
 */
@Component
@Slf4j
public class MessageConsumer {
    //邮件适配器
    @Autowired
    private MailAdapterImpl mailAdapter;
    //修改密码适配器
    @Autowired
    private FindPasswordImpl findPassword;

    //短信适配器对象
    //@Autowired......

    @JmsListener(destination = "register-email-Queue")
    public void register(String msg){
        try{
            log.info("###消息服务收到消息###  消息内容 json:{} ",msg);
            if (StringUtils.isEmpty(msg)){
                return ;
            }
            JSONObject jsonObject = JSONObject.parseObject(msg);
            JSONObject header = jsonObject.getJSONObject("header");
            String interfaceType = header.getString("interfaceType");

            MessageAdapter messageAdapter;
            //判断接口类型
            switch (interfaceType){
                //交给邮件适配器进行发送
                case Mail.SMS_MAIL :
                    JSONObject content = jsonObject.getJSONObject("content");
                    messageAdapter = mailAdapter;
                    messageAdapter.distribute(content);
                    break;
                //交给短信适配器进行发送
                //.....
                //......
                default:
                    break;
            }
        }catch (Exception e){
            log.info("###消息服务出错###  出错内容 error:{} ",e);
        }
    }

    @JmsListener(destination = "findPwd-Queue")
    public void findPassword(String msg) {
        try{
            log.info("###消息服务收到消息###  消息内容 json:{} ",msg);
            if (StringUtils.isEmpty(msg)){
                return ;
            }
            JSONObject jsonObject = JSONObject.parseObject(msg);
            JSONObject content = jsonObject.getJSONObject("content");
            MessageAdapter messageAdapter = findPassword;
            messageAdapter.distribute(content);
        }catch (Exception e){
            log.info("###消息服务出错###  出错内容 error:{} ",e);
        }
    }
}
