package com.info.rabbitmq.consumer.rpc;

import com.info.rabbitmq.consumer.config.DelayedConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xxy
 * @ClassName DelayedConsumer
 * @Description 延时消费
 * @Date 2019/3/5 15:59
 **/
@Component
@RabbitListener(queues = DelayedConfig.QUEUE_NAME)
public class DelayedConsumer {

    @RabbitHandler
    public void delayedConsumer (String msg){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("接收时间:" + sdf.format(new Date()));
        System.out.println("消息内容：" + msg);
    }
}
