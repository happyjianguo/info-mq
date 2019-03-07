package com.info.rabbitmq;

import com.info.mq.entity.User;
import com.info.mq.util.DateUtil;
import com.info.rabbitmq.provider.*;
import org.apache.http.client.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sound.midi.Soundbank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xxy
 * @ClassName RabbitmqTest
 * @Description todo
 * @Date 2019/3/4 15:54
 **/
@SpringBootTest(classes=InfoRabbitmqApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitmqTest {

    @Autowired
    private DirectSender directSender;

    @Autowired
    private TopicSender topicSender;

    @Autowired
    private HeaderSender headerSender;

    @Autowired
    private FanoutSender fanoutSender;

    @Autowired
    private DirectEntitySender directEntitySender;

    @Autowired
    private DelayedSender delayedSender;

    @Autowired
    private AnnotationSender annotationSender;

    @Autowired
    private AckAnnotationSender ackAnnotationSender;

    @Test
    public void testDirectMsg() {
       directSender.sendDirectMsg("当前时间为："+System.currentTimeMillis());
    }


    @Test
    public void test_user() {
        topicSender.user("用户管理！");
    }

    @Test
    public void test_userQuery() {
        topicSender.userQuery("查询用户信息！");
    }


    @Test
    public void test_order() {
        topicSender.order("订单管理！");
    }

    @Test
    public void test_orderQuery() {
        topicSender.orderQuery("查询订单信息！");
    }

    @Test
    public void test_orderDetailQuery() {
        topicSender.orderDetailQuery("查询订单详情信息！");
    }


    @Test
    public void test_creditBank_type() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        headerSender.creditBank(head, "银行授信(部分匹配)");
    }

    @Test
    public void test_creditBank_all() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        head.put("aging", "fast");
        headerSender.creditBank(head, "银行授信(全部匹配)");
    }

    @Test
    public void test_creditFinance_type() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        headerSender.creditFinance(head, "金融公司授信(部分匹配)");
    }

    @Test
    public void test_creditFinance_all() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        head.put("aging", "fast");
        headerSender.creditFinance(head, "金融公司授信(全部匹配)");
    }


    @Test
    public void test_fanout(){
        fanoutSender.generateReports("开始生成报表了。。。");
    }

    @Test
    public void test_directEntity(){
        User user = new User();
        user.setId(12);
        user.setName("testEntity");
        user.setAmount(new BigDecimal(123));
        user.setCreateTime(new Date());
        directEntitySender.directEntitySend(user);
    }

    @Test
    public void test_delayTime(){
        String msg = "延迟发送测试";
        delayedSender.sendDelayed(msg,50000);
    }

    @Test
    public void test_annotation() throws InterruptedException {
        int flag = 0;
        while (true){
            flag ++  ;
            Thread.sleep(5000);
            annotationSender.sendAnnotationMsg("hello world rabbitmq ......"+ flag);
        }
    }


    @Test
    public void test_ack(){
        ackAnnotationSender.sendAckMessage("hello world ack ...." );

    }

    @Test
    public  void test_ack2(){
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", DateUtil.getParaseDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        ackAnnotationSender.sendMessageAck("hello world ack ... ",properties);
    }

    public static void main(String[] args) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", "2016-02-23");
        MessageHeaders mhs = new MessageHeaders(properties);
        System.out.println("格式化map:" + mhs);
        Message msg = MessageBuilder.createMessage("ces", mhs);
        System.out.println("新增msg:" + msg);
        CorrelationData correlationData = new CorrelationData("1234567890");
        System.out.println(correlationData.toString());
    }

}
