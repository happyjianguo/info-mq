package com.info.rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xxy
 * @ClassName Sender
 * @Description todo
 * @Date 2019/3/4 15:46
 **/
@Component
public class DirectSender {

    @Autowired
    private AmqpTemplate template;

    public void sendDirectMsg(String msg) {
        System.out.println("发送方被调用。。。" + msg);
        template.convertAndSend("notify.payment",msg);
    }


}
