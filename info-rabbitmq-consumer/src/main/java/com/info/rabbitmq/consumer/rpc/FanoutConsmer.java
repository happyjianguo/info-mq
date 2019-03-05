package com.info.rabbitmq.consumer.rpc;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xxy
 * @ClassName FanoutConsmer
 * @Description todo
 * @Date 2019/3/5 10:52
 **/
@Component
public class FanoutConsmer {
    @RabbitHandler
    @RabbitListener(queues = "api.report.payment")
    public void payment(String msg) {
        System.out.println("api.report.payment receive message: " + msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "api.report.refund")
    public void refund(String msg) {
        System.out.println("api.report.refund receive message: " + msg);
    }
}
