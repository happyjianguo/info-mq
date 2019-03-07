package com.info.rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xxy
 * @ClassName AnnotationSender
 * @Description todo
 * @Date 2019/3/6 10:21
 **/
@Component
public class AnnotationSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Value("${mq.config.exchange}")
    private String exchange;

    @Value("${mq.config.info.key}")
    private String routingKey;

    public void sendAnnotationMsg(String msg) {
        System.out.println("消息" + msg + "，routingKey:" + routingKey + "exchange:"+ exchange);
        amqpTemplate.convertAndSend(exchange,routingKey,msg);
    }
}
