package info.rocket.mq.producer;

import com.info.mq.entity.User;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author xxy
 * @ClassName RocketProducerOrder
 * @Description todo  顺序消息
 * @Date 2019/9/5 16:13
 **/
public class RocketProducerOrder {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new  DefaultMQProducer("info-order-producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        List<User> userList = User.buildUserList();
        for (int i= 0 ;i<userList.size();i++) {
            User user = userList.get(i);
            Message message = new Message("info-order","order","i"+i,user.toString().getBytes());
            producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    int id = (int) o;
                    int i1 = id % list.size();
                    MessageQueue messageQueue = list.get(i1);
                    return messageQueue;
                }
            }, user.getId());
            Thread.sleep(2);
        }
        producer.shutdown();
        System.out.println("producer send success");
    }

}
