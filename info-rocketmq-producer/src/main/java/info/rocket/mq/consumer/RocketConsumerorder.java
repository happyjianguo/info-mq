package info.rocket.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author xxy
 * @ClassName RocketConsumerorder
 * @Description todo  顺序消息
 * @Date 2019/9/5 16:14
 **/
public class RocketConsumerorder {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("info-order-consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("info-order", "*");
        consumer.registerMessageListener(new MessageListenerOrderly() {

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for(MessageExt messageExt : list){
                    System.out.println("线程名称：【"+Thread.currentThread().getName()+"】消费消息："+new String(messageExt.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }

        });
        consumer.start();
        System.out.println("consumer start success");
    }

}
