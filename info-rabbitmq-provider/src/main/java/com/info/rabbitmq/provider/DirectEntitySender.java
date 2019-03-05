package com.info.rabbitmq.provider;

import com.info.mq.entity.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xxy
 * @ClassName DirectEntitySender
 * @Description todo
 * @Date 2019/3/5 14:18
 **/
@Component
public class DirectEntitySender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void directEntitySend(User user){
        System.out.println("发送消息：" + user.toString());
        amqpTemplate.convertAndSend("direct.entity",user);
    }
}

