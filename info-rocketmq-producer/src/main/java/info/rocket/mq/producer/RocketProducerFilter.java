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
 * @Description todo  过滤消息
 * @Date 2019/9/5 16:13
 **/
public class RocketProducerFilter {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new  DefaultMQProducer("info-filter-producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message(
                    "info-filter",
                    "info-tag",
                    "info",
                    ("hello-info-filter"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            message.putUserProperty("i",String.valueOf(i));
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult.toString());
        }
        producer.shutdown();
    }

}
