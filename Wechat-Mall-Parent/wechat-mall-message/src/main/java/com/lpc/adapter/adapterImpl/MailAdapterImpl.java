package com.lpc.adapter.adapterImpl;

import com.alibaba.fastjson.JSONObject;
import com.lpc.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Lin
 * @Date 2019/12/10
 *
 * 邮件类服务实现消息适配器进行转发消息
 */
@Component
@Slf4j
public class MailAdapterImpl implements MessageAdapter {
    //注入java发邮件的类JavaMailSender
    @Autowired
    private JavaMailSender mailSender;
    //发送者 在yml中设置的
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void distribute(JSONObject content) {
        String receiver_email = content.getString("email");
        String receiver_name = content.getString("userName");
        log.info("###接收到的邮件消息 receiver_email:{}  receiver_name:{}",receiver_email,receiver_name);
        //开始发送邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);   //发送者
        simpleMailMessage.setTo(receiver_email);  //接收者
        simpleMailMessage.setSubject("注册微信商城");
        simpleMailMessage.setText("恭喜您:" + receiver_name + "，您已成为会员！");
        log.info("###注册者邮箱 receiver_email:{}",receiver_email);
        mailSender.send(simpleMailMessage);
    }
}
