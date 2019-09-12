package info.rocket.mq.consumer;

import com.info.mq.util.DateUtil;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xxy
 * @ClassName TransactionListenerImpl
 * @Description todo
 * @Date 2019/9/9 11:26
 **/
@Component
public class TransactionListenerImpl implements TransactionListener {



    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();


    /**
     * @Author xxy
     * @Description  执行本地事务
     * @Date 14:27 2019/9/9
     * @Param [message, o]
     * @return org.apache.rocketmq.client.producer.LocalTransactionState
     **/
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        String transactionId = message.getTransactionId();
        // 0 执行中 1 本地事务执行成功 2 本地事务执行失败
        localTrans.put(transactionId,0);
        // 业务执行 处理本地事务 service
        System.out.println("执行本地事务。。。。start ==="  + transactionId);
        try {
            System.out.println(DateUtil.getParaseDate(new Date(),"yyyy-MM-dd HH:ss:mm") +"正在执行本地事务。。。。process===" + transactionId);
            Thread.sleep(1000*60+1000);
            System.out.println(DateUtil.getParaseDate(new Date(),"yyyy-MM-dd HH:ss:mm") + "正在执行本地事务。。。。success===" + transactionId);
            localTrans.put(transactionId,1);
        } catch (InterruptedException e) {
            System.out.println("执行本地事务失败。。。。fail===" + transactionId);
            e.printStackTrace();
            localTrans.put(transactionId,2);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        System.out.println("执行本地事务。。。。end===" + transactionId);
        return LocalTransactionState.COMMIT_MESSAGE;



    }

    /**
     * @Author xxy
     * @Description 校验本地事务  消息回查
     * status0 = 0 消息会回调15次，然后扔掉，消费者收不到消息；
     * status1 = 1 消息会在第一次回调后，发送给消费者并被消费；
     * status2 = 2 消息会在第一次回调后，扔掉消息，消费者收不到消息。
     * @Date 14:29 2019/9/9
     * @Param [messageExt]
     * @return org.apache.rocketmq.client.producer.LocalTransactionState
     **/
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("checkLocalTransaction:"+DateUtil.getParaseDate(new Date(),"yyyy-MM-dd HH:ss:mm"));
        String transactionId = messageExt.getTransactionId();
        System.out.println("校验本地事务start。。。" + transactionId);
        //获取对应事务ID的状态
        Integer status = localTrans.get(transactionId);
        System.out.println("校验本地事务start。。。状态==" + status);
        if(status != null){
            switch (status) {
                case 0:
                    System.out.println("校验本地事务process。。。00000==" + transactionId);
                    return LocalTransactionState.UNKNOW;
                case 1:
                    System.out.println("校验本地事务process。。。11111==" + transactionId);
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    System.out.println("校验本地事务process。。。22222==" + transactionId);
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                default:
                    System.out.println("我也不知道该干啥了");
            }
        }
        System.out.println("校验本地事务end。。。"+LocalTransactionState.COMMIT_MESSAGE + "---------"+ transactionId);
        return LocalTransactionState.COMMIT_MESSAGE;
    }


    public static void main(String[] args) {
        String string = "abc";
        switch (string){
            case "abc":
                System.out.println("aa"+string);
                break;
            case "def":
                System.out.println("dd"+string);
                break;
        }
    }
}
