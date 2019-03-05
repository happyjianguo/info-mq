package com.info.rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xxy
 * @ClassName HeaderSender
 * @Description todo
 * @Date 2019/3/5 10:03
 **/
@Component
public class HeaderSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void creditBank(Map<String, Object> head, String msg){
        System.out.println("credit.bank send message: "+msg);
        rabbitTemplate.convertAndSend("creditBankExchange", "credit.bank", getMessage(head, msg));
    }

    public void creditFinance(Map<String, Object> head, String msg){
        System.out.println("credit.finance send message: "+msg);
        rabbitTemplate.convertAndSend("creditFinanceExchange", "credit.finance", getMessage(head, msg));
    }

    private Message getMessage(Map<String, Object> head, Object msg){
        MessageProperties messageProperties = new MessageProperties();
        for (Map.Entry<String, Object> entry : head.entrySet()) {
            messageProperties.setHeader(entry.getKey(), entry.getValue());
        }
        MessageConverter messageConverter = new SimpleMessageConverter();
        Message message = messageConverter.toMessage(msg, messageProperties);
        System.out.println(message+"封装的消息");
        return message;
    }
}
