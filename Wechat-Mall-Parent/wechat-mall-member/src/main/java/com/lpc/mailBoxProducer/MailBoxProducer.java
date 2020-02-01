package com.lpc.mailBoxProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * @author Lin
 * @Date 2019/12/9
 *
 * 往activeMQ推送邮件消息
 */
@Service
public class MailBoxProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(Destination destination, String msg){
        jmsMessagingTemplate.convertAndSend(destination,msg);   //参数依次是：消息队列名称,发送的消息内容(报文格式是json)
    }
}
