package com.info.rabbitmq.provider;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xxy
 * @ClassName AckAnnotationSender
 * @Description todo
 * @Date 2019/3/6 15:23
 **/
@Component
public class AckAnnotationSender{

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${mq.config.exchange}")
    private String exchange;

    @Value("${mq.config.info.key}")
    private String routingKey;

    public void sendAckMessage(String msg){

        /**
         * 关于 msgSendConfirmCallBack 和 msgSendReturnCallback 的回调说明：
         * 1.如果消息没有到exchange,则confirm回调,ack=false
         * 2.如果消息到达exchange,则confirm回调,ack=true
         * 3.exchange到queue成功,则不回调return
         * 4.exchange到queue失败,则回调return(需设置mandatory=true,否则不回调,消息就丢了)
         */

        /**
         * 消息确认机制
         * Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，
         * 哪些可能因为broker宕掉或者网络失败的情况而重新发布。
         * 确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)
         * 在channel为事务时，不可引入确认模式；同样channel为确认模式下，不可使用事务。
         * @return
         */


        //若消息找不到对应的 Exchange会先触发 returncallback
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("returnedMessage sender return success message ...." + message.toString());
                System.out.println("returnedMessage sender return success replyCode .... " + replyCode);
                System.out.println("returnedMessage sender return success replyText ...." + replyText);
                System.out.println("returnedMessage sender return success exchange ... ." + exchange);
                System.out.println("returnedMessage sender return success routingKey ...." + routingKey);
            }
        });

        //confirmcallback用来确认消息是否有送达消息队列
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println(cause+ "========");
                System.out.println(ack + "========");
                System.out.println(correlationData.toString() + "========");
                if (ack) {
                    System.out.println("msg confirm success ..........");
                } else {
                    //处理丢失的消息（nack）
                    System.out.println("msg confirm fails ............");
                }
            }
        });

        rabbitTemplate.convertAndSend(exchange,routingKey,msg);
        System.out.println("发送成功："+ msg);
    }



    //回调函数: confirm确认
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData: " + correlationData);
            System.err.println("ack: " + ack);
            if(!ack){
                System.err.println("异常处理....");
            }
        }
    };

    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange1, String routingKey1) {
            System.err.println("return exchange: " + exchange1 + ", routingKey: "
                    + routingKey1 + ", replyCode: " + replyCode + ", replyText: " + replyText);
        }
    };

    public  void sendMessageAck(String message, Map<String, Object> properties){
        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = (Message) MessageBuilder.createMessage(message, mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        //id + 时间戳  全局唯一
        CorrelationData correlationData = new CorrelationData("1234567890");
        rabbitTemplate.convertAndSend(exchange, routingKey, msg, correlationData);
    }

}
