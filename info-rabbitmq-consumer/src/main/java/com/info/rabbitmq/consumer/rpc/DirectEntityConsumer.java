package com.info.rabbitmq.consumer.rpc;

import com.info.mq.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationEvent;

/**
 * @author xxy
 * @ClassName DirectEntityConsumer
 * @Description todo
 * @Date 2019/3/5 14:15
 **/
@Component
public class DirectEntityConsumer {

    @RabbitListener(queues = "direct.entity")
    public void receive(User user) {
        System.out.println("direct.entity receive message: " + user);
    }
}
