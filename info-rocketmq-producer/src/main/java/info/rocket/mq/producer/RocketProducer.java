package info.rocket.mq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author xxy
 * @ClassName RocketProducer
 * @Description todo
 * @Date 2019/9/5 16:13
 **/
public class RocketProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new  DefaultMQProducer("info-first-rocketmq-producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 3; i++) {
            Message message = new Message(
                    "info-topic-second",
                    "info-tag",
                    "info1001",
                    "hello-info-first-rocketmq".getBytes(RemotingHelper.DEFAULT_CHARSET));
            message.setDelayTimeLevel(3);
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult.toString());
        }
        producer.shutdown();
    }

}
