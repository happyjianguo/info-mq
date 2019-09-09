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
 * @Description todo
 * @Date 2019/9/5 16:13
 **/
public class RocketProducerTransaction {

    public static void main(String[] args) throws Exception{
        TransactionListener transactionListener = new TransactionListenerImpl();

        TransactionMQProducer transactionMQProducer = new TransactionMQProducer("info-transaction-producer2");

        transactionMQProducer.setNamesrvAddr("127.0.0.1:9876");

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


        for(int i = 0 ;i<10 ;i++){
            Message message = new Message(
                    "info-transaction",
                    "info-tag"+i,
                    "info-transaction-100"+i,
                    ("hello-info-first-rocketmq"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            TransactionSendResult transactionSendResult = transactionMQProducer.sendMessageInTransaction(message, "info-transaction-hello");
            System.out.println(transactionSendResult.toString());
            Thread.sleep(10);
        }

        // 保持会儿 对于UNKNOW的消息需要在1分钟后回调
        //Thread.sleep(1000 * 60 * 60 );
        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        transactionMQProducer.shutdown();
    }

}
