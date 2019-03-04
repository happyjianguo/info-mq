package com.info.rabbitmq.consumer.rpc;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xxy
 * @ClassName TopicConsumerApiPayment
 * @Description todo
 * @Date 2019/3/4 17:10
 **/
@Component
public class TopicConsumerApiPayment {

    @RabbitHandler
    @RabbitListener(queues = "api.payment")
    public void order(String msg) {
        System.out.println("api.payment.order receive message: "+msg);
    }

}
