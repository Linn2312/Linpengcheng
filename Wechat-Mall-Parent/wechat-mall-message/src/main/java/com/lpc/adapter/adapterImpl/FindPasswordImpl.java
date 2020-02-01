package com.lpc.adapter.adapterImpl;

import com.alibaba.fastjson.JSONObject;
import com.lpc.adapter.MessageAdapter;
import com.lpc.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Lin
 * @Date 2020/1/26
 *
 * 发送修改密码链接给用户
 */
@Component
@Slf4j
public class FindPasswordImpl implements MessageAdapter {
    //注入java发邮件的类JavaMailSender
    @Autowired
    private JavaMailSender mailSender;
    //发送者 在yml中设置的
    @Value("${spring.mail.username}")
    private String sender;
    @Override
    public void distribute(JSONObject content) {
        String receiver_email = content.getString("email");
        log.info("###接收到的邮件消息 receiver_email:{}",receiver_email);
        //开始发送邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);   //发送者
        simpleMailMessage.setTo(receiver_email);  //接收者
        simpleMailMessage.setSubject("微信商城———修改密码");
        simpleMailMessage.setText("点击链接进行修改密码："+Constants.UPDATEPASSWORD);
        log.info("###注册者邮箱 receiver_email:{}",receiver_email);
        mailSender.send(simpleMailMessage);
    }
}
