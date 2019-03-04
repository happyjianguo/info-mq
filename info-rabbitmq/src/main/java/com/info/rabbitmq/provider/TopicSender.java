package com.info.rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xxy
 * @ClassName TopicSender
 * @Description todo
 * @Date 2019/3/4 17:16
 **/
@Component
public class TopicSender {
    @Autowired
    AmqpTemplate template;

    /**
     * @Author xxy
     * @Description 添加一个user()方法，发送消息至coreExchange交换机且routingKey为api.core.user
     * @Date 17:53 2019/3/4
     * @Param [msg]
     * @return void
     **/
    public void user(String msg){
        System.out.println("api.core.user send message: "+msg);
        template.convertAndSend("coreExchange", "api.core.user", msg);
    }

    /**
     * @Author xxy
     * @Description 添加一个userQuery()方法，发送消息至coreExchange交换机且routingKey为api.core.user.query
     * @Date 17:54 2019/3/4
     * @Param [msg]
     * @return void
     **/
    public void userQuery(String msg){
        System.out.println("api.core.user.query send message: "+msg);
        template.convertAndSend("coreExchange", "api.core.user.query", msg);
    }

    /**
     * @Author xxy
     * @Description 添加一个order()方法，发送消息至paymentExchange交换机且routingKey为api.payment.order
     * @Date 17:54 2019/3/4
     * @Param [msg]
     * @return void
     **/
    public void order(String msg){
        System.out.println("api.payment.order send message: "+msg);
        template.convertAndSend("paymentExchange", "api.payment.order", msg);
    }

    /**
     * @Author xxy
     * @Description 添加一个orderQuery()方法，发送消息至paymentExchange交换机且routingKey为api.payment.order.query
     * @Date 17:54 2019/3/4
     * @Param [msg]
     * @return void
     **/
    public void orderQuery(String msg){
        System.out.println("api.payment.order.query send message: "+msg);
        template.convertAndSend("paymentExchange", "api.payment.order.query", msg);
    }

    /**
     * @Author xxy
     * @Description
     * 添加一个orderDetailQuery()方法，发送消息至paymentExchange
     * 交换机且routingKey为api.payment.order.detail.query
     * @Date 17:54 2019/3/4
     * @Param [msg]
     * @return void
     **/
    public void orderDetailQuery(String msg){
        System.out.println("api.payment.order.detail.query send message: "+msg);
        template.convertAndSend("paymentExchange", "api.payment.order.detail.query", msg);
    }

}
