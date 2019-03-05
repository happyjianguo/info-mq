package com.info.rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xxy
 * @ClassName FanoutSender
 * @Description todo
 * @Date 2019/3/5 10:53
 **/
@Component
public class FanoutSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void generateReports(String msg){
        System.out.println("api.generate.reports send message: "+msg);
        rabbitTemplate.convertAndSend("reportExchange", "api.generate.reports", msg);
    }
}
