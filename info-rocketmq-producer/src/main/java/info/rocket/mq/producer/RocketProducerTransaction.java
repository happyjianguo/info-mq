package info.rocket.mq.producer;

import info.rocket.mq.consumer.TransactionListenerImpl;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * @author xxy
 * @ClassName RocketProducerTransaction
 * @Description todo  事务消息
 * @Date 2019/9/5 16:13
 **/
public class RocketProducerTransaction {

    public static void main(String[] args) throws Exception{
        TransactionListener transactionListener = new TransactionListenerImpl();

        TransactionMQProducer transactionMQProducer = new TransactionMQProducer("info-transaction-producer-09-10");

        transactionMQProducer.setNamesrvAddr("127.0.0.1:9876");

        transactionMQProducer.setRetryTimesWhenSendFailed(3);//如果该条消息在1S内没有发送成功，那么重试3次

        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });

        transactionMQProducer.setExecutorService(executorService);

        transactionMQProducer.setTransactionListener(transactionListener);

        transactionMQProducer.start();

        Message message = new Message(
                "info-transaction-09-10",
                "info-tag-09-10",
                "info-transaction--09-10",
                ("hello-info-first-rocketmq-09-10").getBytes(RemotingHelper.DEFAULT_CHARSET));
        transactionMQProducer.sendMessageInTransaction(message, "info-transaction");
        transactionMQProducer.shutdown();
    }

}
