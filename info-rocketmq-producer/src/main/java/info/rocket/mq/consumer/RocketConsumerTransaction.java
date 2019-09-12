package info.rocket.mq.consumer;

import com.info.mq.util.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author xxy
 * @ClassName RocketConsumerTransaction
 * @Description todo  事务消息
 * @Date 2019/9/5 16:14
 **/
// https://www.jianshu.com/p/c4a3064152ae
public class RocketConsumerTransaction {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("info-transaction-consumer-09-10");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("info-transaction-09-10", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    String topic = messageExt.getTopic();
                    String tags = messageExt.getTags();
                    byte[] body = messageExt.getBody();
                    try {
                        System.out.println("接收消息的时间："+DateUtil.getParaseDate(new Date(),"yyyy-MM-dd HH:ss:mm"));
                        System.out.println("接收到的消息：主题："+topic+"，tag:"+tags+",消息body："+new String(body, RemotingHelper.DEFAULT_CHARSET));
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
