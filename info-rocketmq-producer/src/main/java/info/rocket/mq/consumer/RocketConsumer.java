package info.rocket.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author xxy
 * @ClassName RocketConsumer
 * @Description todo  普通消息，延迟消息
 * @Date 2019/9/5 16:14
 **/
public class RocketConsumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("info-first-rocketmq-consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("info-topic-second", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    String topic = messageExt.getTopic();
                    String tags = messageExt.getTags();
                    byte[] body = messageExt.getBody();
                    try {
                        System.out.println("Receive message[msgId=" + messageExt.getMsgId() + "] "
                                + (System.currentTimeMillis() - messageExt.getStoreTimestamp()) + "ms later");
                        System.out.println("接收到的消息：主题："+topic+"，tag:"+tags+",消息主题："+new String(body, RemotingHelper.DEFAULT_CHARSET));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("start success");
    }

}
