package info.rocket.mq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author xxy
 * @ClassName RocketProducerBroadcast
 * @Description todo  广播消息
 * @Date 2019/9/6 11:35
 **/
public class RocketProducerBroadcast {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new  DefaultMQProducer("info-broadcast-producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setRetryTimesWhenSendFailed(3);
        producer.start();
        for (int i = 0; i < 3; i++) {
            Message message = new Message(
                    "info-broad",
                    "info-tag",
                    "info-broad1001",
                    "hello-info-broad-rocketmq".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult.toString());
        }
        producer.shutdown();
    }
}
