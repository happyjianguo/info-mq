package com.info.rabbitmq.consumer.rpc;

import com.info.mq.util.DateUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author xxy
 * @ClassName AckAnnotationConsumer
 * @Description todo
 * @Date 2019/3/6 14:52
 **/
@Component
@RabbitListener(bindings = @QueueBinding(
        value=@Queue(value = "${mq.config.queue}",autoDelete = "false",durable = "true"),
        exchange = @Exchange(value = "${mq.config.exchange}",type = ExchangeTypes.DIRECT,durable = "true"),
        key = "${mq.config.info.key}"))
public class AckAnnotationConsumer {

    @RabbitHandler
    public void process(String hello, Channel channel, Message message, CorrelationData correlationData) throws IOException {
        System.out.println("HelloReceiver收到  : " + hello +"收到时间 : "+ DateUtil.getParaseDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        System.out.println("封装的corr------11" + correlationData.toString());
        System.out.println("封装corr222"+message.toString());
        System.out.println("封装的corr------33" + message.getMessageProperties().getCorrelationId());
        try {
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了
            //否则消息服务器以为这条消息没处理掉 后续还会在发

            /**
             *
             * 第二个参数 multiple 取值为 false 时，表示通知 RabbitMQ 当前消息被确认
             * 如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认。（批量确认针对的是整个信道）
             *
             **/

            //向服务端发送ack，确认我收到消息了  【确认消息】
            //long deliveryTag, 该消息的index
            //boolean multiple 是否批量.true:将一次性ack所有小于deliveryTag的消息。
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

            //ack返回false，并重新回到队列，api里面解释得很清楚
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);

            //第一个参数指定 delivery tag， 【取消确认】第二个参数说明如何处理这个失败消息。
            // requeue 值为 true 表示该消息重新放回队列头， 会一直调用该方法。
            //值为 false 表示放弃这条消息,相当于告诉队列可以直接删除掉 只调用一次
            //long deliveryTag, 该消息的index
            //boolean requeue 被拒绝的是否重新入队列
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);

            // int prefetchSize,0
            // int prefetchCount, 会告诉RabbitMQ不要同时给一个消费者推送多于N个消息，
            // 即一旦有N个消息还没有ack，则该consumer将block掉，直到有消息ack
            // boolean global
            //channel.basicQos(1,1,true);
            System.out.println("receiver success");
        } catch (Exception e) {
            e.printStackTrace();
            //丢弃这条消息

            //最后一个参数 requeue 设置为true 会把消费失败的消息从新添加到队列的尾端，设置为false不会重新回到队列
            // long deliveryTag,  该消息的index
            // boolean multiple, 是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
            // boolean requeue 被拒绝的是否重新入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            System.out.println("receiver fail");
        }

    }
}
