package com.info.rabbitmq.consumer.rpc;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xxy
 * @ClassName ConsumerMsg
 * @Description todo
 * @Date 2019/3/4 16:06
 **/
@Component
@RabbitListener(queues = "notify.payment")
public class DirectConsumerMsg {

    @RabbitHandler
    public void consumer(String msg){
        System.out.println("接收到的消息是：" + msg);
    }
}
